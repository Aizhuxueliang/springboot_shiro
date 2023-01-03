package com.example.demo.config;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByName(userName);
        if (user == null) {
            return null;
        }
        //角色集合
        List<String> stringRoleList = new ArrayList<>();
        //权限集合
        List<String> stringPermissionList = new ArrayList<>();

        user.getRoleList().forEach(role -> {
            stringRoleList.add(role.getName());
            List<Permission> permissionList = role.getPermissionList();
            permissionList.forEach(permission -> {
                stringPermissionList.add(permission.getName());
            });
        });

        /*//43-49 realized
        stringRoleList = user.getRoleList().stream().map(role -> {
            stringPermissionList.addAll(role.getPermissionList().stream().map(Permission::getName).collect(Collectors.toList())) ;
            return role.getName();
        }).collect(Collectors.toList());*/

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addStringPermissions(stringPermissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //从token中获取用户信息
        String uesrName = (String) authenticationToken.getPrincipal();
        User user = userService.findUserByName(uesrName);
        if (user == null) {
            return null;
        }
        //密码
        String pwd = user.getPassword();
        if (pwd == null || "".equals(pwd)) {
            return null;
        }
        return new SimpleAuthenticationInfo(uesrName, pwd, this.getClass().getName());
    }
}
