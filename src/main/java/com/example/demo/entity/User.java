package com.example.demo.entity;

/**
 * 用户实体
 */
public class User {
    private int userId;//用户ID
    private String userPwd;//用户密码
    private String userName;//用户名称
    private String userRole;//用户角色

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPwd=" + userPwd +
                ", userName='" + userName + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
