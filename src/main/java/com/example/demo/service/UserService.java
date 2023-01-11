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

/**
 * 用户服务层
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

    public Map<String, Object> findPermissionListByRoleId(int roleId){
        //角色具有的权限集合
        List<Permission> bePermissionList = permissionMapper.findByPermissionListByRoleId(roleId);
        //角色没有的权限集合
        Collection<Permission> notPermissionList = permissionMapper.findNotPermissionListByRoleId(roleId);
        //所有权限集合
        Collection<Permission> allPermissionList = CollectionUtils.union(bePermissionList, notPermissionList);
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

    public Map<String, Object> addRole(Role role){
        int addRole = roleMapper.addRole(role);
        return this.resultMap("addRole", addRole, "",  "", "", "");
    }

    public List<User> queryPage(Integer startRows) {
        return userMapper.queryPage(startRows);
    }

    public int getRowCount() {
        return userMapper.getRowCount();
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public int modify(User user){
        return userMapper.modify(user);
    }

    public int delete(int userId){
        return userMapper.delete(userId);
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