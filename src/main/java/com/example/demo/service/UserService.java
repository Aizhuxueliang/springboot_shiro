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
        Map reusltMap = new HashMap<>();
        //角色具有的权限集合
        List<Permission> bePermissionList = permissionMapper.findByPermissionListByRoleId(roleId);
        reusltMap.put("bePermissionList", bePermissionList);
        //角色没有的权限集合
        Collection notPermissionList = permissionMapper.findNotPermissionListByRoleId(roleId);
        reusltMap.put("notPermissionList", notPermissionList);
        //所有权限集合
        Collection<Permission> allPermissionList = CollectionUtils.union(bePermissionList, notPermissionList);
        reusltMap.put("allPermissionList", allPermissionList);
        return reusltMap;
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

}