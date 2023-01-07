package com.example.demo.config;

import com.example.demo.entity.Permission;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
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
        Set<String> stringRoleSet = new HashSet<>();
        //权限集合
        Set<String> stringPermissionSet = new HashSet<>();
        //往集合里面塞角色名称和权限名称
        user.getRoleList().forEach(role -> {
            stringRoleSet.add(role.getName());
            role.getPermissionList().forEach(permission -> {
                stringPermissionSet.add(permission.getName());
            });
        });
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleSet);
        simpleAuthorizationInfo.addStringPermissions(stringPermissionSet);
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
        //权限集合
        Set<String> stringPermissionSet = new HashSet<>();
        user.getRoleList().forEach(role -> {
            stringPermissionSet.addAll(role.getPermissionList().stream().map(Permission::getName).collect(Collectors.toList()));
        });
        SecurityUtils.getSubject().getSession().setAttribute("permissions", stringPermissionSet);
        //密码
        String pwd = user.getPassword();
        if (pwd == null || "".equals(pwd)) {
            return null;
        }
        return new SimpleAuthenticationInfo(uesrName, pwd, this.getClass().getName());
    }
}
