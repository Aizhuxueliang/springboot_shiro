package com.example.demo.service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.RoleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务层
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        //用户角色的集合
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    public Map<String, Object> findRoleListByUserId(int userId){
        //用戶具有的角色集合
        List<Role> beRoleList = roleMapper.findRoleListByUserIdNotPermission(userId);
        //用戶没有的角色集合
        List<Role> notRoleList = roleMapper.findNotRoleListByUserIdNotPermission(userId);
        //所有角色集合
        Collection<?> allRoleList = CollectionUtils.union(beRoleList, notRoleList);
        return this.resultMap("beRoleList", beRoleList, "notRoleList", notRoleList, "allRoleList", allRoleList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> updateUserRole(User user){
        int removeUserRole = userMapper.removeUserRoleByUserId(user.getId());
        int addUserRole = 0;
        if (user.getRoleList().size()!=0 && user.getRoleList()!=null){
            addUserRole = userMapper.addUserRole(user);
        }
        return this.resultMap("removeUserRole", removeUserRole, "addUserRole", addUserRole, "", "");
    }

    public Map<String, Object> findPermissionListByRoleId(int roleId){
        //角色具有的权限集合
        List<Permission> bePermissionList = permissionMapper.findByPermissionListByRoleId(roleId);
        //角色没有的权限集合
        List<Permission> notPermissionList = permissionMapper.findNotPermissionListByRoleId(roleId);
        //所有权限集合
        Collection<?> allPermissionList = CollectionUtils.union(bePermissionList, notPermissionList);
        return this.resultMap("bePermissionList", bePermissionList, "notPermissionList", notPermissionList, "allPermissionList", allPermissionList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> updateRolePermission(Role role){
        int removeRolePermission = roleMapper.removeRolePermissionByRoleId(role.getId());
        int addRolePermission = 0;
        if (role.getPermissionList().size()!=0 && role.getPermissionList()!=null){
            addRolePermission = roleMapper.addRolePermission(role);
        }
        return this.resultMap("removeRolePermission", removeRolePermission, "addRolePermission", addRolePermission, "", "");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> removeRole(Role role){
        int removeRolePermission = roleMapper.removeRolePermissionByRoleId(role.getId());
        int removeRole = roleMapper.removeRoleByRoleId(role.getId());
        int removeUserRole = userMapper.removeUserRoleByRoleId(role.getId());
        return this.resultMap("removeRolePermission", removeRolePermission, "removeRole", removeRole, "removeUserRole", removeUserRole);
    }

    public Map<String, Object> queryUserListPage(User user){
        //当前页页码
        int pageNow = user.getReserve1() < 1 ? 1 : user.getReserve1();
        //当前页第一行索引
        user.setReserve1(5*(pageNow - 1));
        List<User> userListPage = userMapper.queryUserListPage(user);
        int userRowCount = userMapper.getUserRowCount(user);
        return this.resultMap("userListPage", userListPage, "userRowCount",  userRowCount, "", "");
    }

    public Map<String, Object> addRole(Role role){
        int addRole = roleMapper.addRole(role);
        return this.resultMap("addRole", addRole, "",  "", "", "");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> removeUser(User user){
        int removeUser = userMapper.removeUserByUserId(user.getId());
        int removeUserRole = userMapper.removeUserRoleByUserId(user.getId());
        return this.resultMap("removeUser", removeUser, "removeUserRole", removeUserRole, "", "");
    }

    public Map<String, Object> insertUser(User user) {
        int addUser = userMapper.insertUser(user);
        return this.resultMap("addUser", addUser, "", "", "", "");
    }

    public Map<String, Object> resultMap(String str1, Object obj1, String str2, Object obj2, String str3, Object obj3){
        Map<String, Object> resultMap = new HashMap<>();
        if (!"".equals(str1) || !"".equals(obj1))
            resultMap.put(str1, obj1);
        if (!"".equals(str2) || !"".equals(obj2))
            resultMap.put(str2, obj2);
        if (!"".equals(str3) || !"".equals(obj3))
            resultMap.put(str3, obj3);
        return resultMap;
    }

}