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

    public List<User> findByName(String userName) {
        return userMapper.findByName(userName);
    }

    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        //用户角色的集合
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    public Map findPermissionListByRoleId(int roleId){
        //角色具有的权限集合
        List<Permission> bePermissionList = permissionMapper.findByPermissionListByRoleId(roleId);
        //角色没有的权限集合
        Collection notPermissionList = permissionMapper.findNotPermissionListByRoleId(roleId);
        //所有权限集合
        Collection<Permission> allPermissionList = CollectionUtils.union(bePermissionList, notPermissionList);
        return reuslt("bePermissionList", bePermissionList, "notPermissionList", notPermissionList, "allPermissionList", allPermissionList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map updateRolePermission(Role role){
        int remove = roleMapper.removeRolePermissionByRoleId(role.getId());
        int add =0;
        if (role.getPermissionList().size()!=0 && role.getPermissionList()!=null){
            add = roleMapper.addRolePermission(role);
        }
        return reuslt("remove", remove, "add", add, "", "");
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

    public List<User> listUser(){
        return userMapper.listUser();
    }

    public int modify(User user){
        return userMapper.modify(user);
    }

    public int delete(int userId){
        return userMapper.delete(userId);
    }

    public Map reuslt(String str1, Object obj1, String str2, Object obj2, String str3, Object obj3){
        Map reusltMap = new HashMap<String, Object>();
        if (!"".equals(str1) || !"".equals(obj1))
            reusltMap.put(str1, obj1);
        if (!"".equals(str2) || !"".equals(obj2))
            reusltMap.put(str2, obj2);
        if (!"".equals(str3) || !"".equals(obj3))
            reusltMap.put(str3, obj3);
        return reusltMap;
    }

}