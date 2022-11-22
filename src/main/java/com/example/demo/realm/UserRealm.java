package com.example.demo.realm;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


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
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        sai.addRole((String) subject.getPrincipal());
        return sai;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) authenticationToken;
        // 获取当前用户
        User user = userService.findUserByName(upt.getUsername());
        if (user == null) {
            return null;
        }
        // Subject subject = SecurityUtils.getSubject();
        // 获取当前用户的session
        // Session seesion = subject.getSession();
        if (!upt.getUsername().equals(user.getUserName())){
            return null;
        }

        return new SimpleAuthenticationInfo(user, user.getUserPwd(), upt.getUsername());
    }
}
