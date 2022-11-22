package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;

import java.util.List;

/**
 * 用户服务层
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> findByName(String userName) {
        return userMapper.findByName(userName);
    }

    public User findUserByName(String userName) {
        return userMapper.findUserByName(userName);
    }

    public List<User> queryPage(Integer startRows) {
        return userMapper.queryPage(startRows);
    }

    public int getRowCount() {
        return userMapper.getRowCount();
    }

    public User insertUser(User user) {
        //读取shiro.ini文件
        IniSecurityManagerFactory factory=new IniSecurityManagerFactory("classpath:shiro.ini");
        //工厂创建缓存管理器
        SecurityManager securityManager = factory.createInstance();
        //缓存管理器交给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //通过SecurityUtils获得登录主体
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.isAuthenticated());//false
        //创建登录令牌
        UsernamePasswordToken token=new UsernamePasswordToken("zs","123");
        //登录
        subject.login(token);
        System.out.println(subject.isAuthenticated());//true
        //登出
        subject.logout();




        userMapper.insertUser(user);
        return user;
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

    public User findUserById(int userId){
        return userMapper.findUserById(userId);
    }

}