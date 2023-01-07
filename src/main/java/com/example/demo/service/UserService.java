package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务层
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

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