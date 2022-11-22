package com.shiroTest.demo;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description：自定义Realm
 * @Author：
 * @Date：2020/12/28 8:41 下午
 * @Versiion：1.0
 */
public class CustomRealm extends AuthorizingRealm {
    private final Map<String, String> userInfoMap = new HashMap<>();
    //role-->permission
    private final Map<String, Set<String>> permissionMap = new HashMap<>();
    //user-->role
    private final Map<String, Set<String>> roleMap = new HashMap<>();

    /**
     * 代码块初始化数据
     */
    {
        userInfoMap.put("laochen", "123");
        userInfoMap.put("laowang", "456");
        //================================
        Set<String> set1 = new HashSet<>();
        set1.add("video:find");
        set1.add("video:buy");
        Set<String> set2 = new HashSet<>();
        set2.add("video:add");
        set2.add("video:delete");
        permissionMap.put("laochen", set1);
        permissionMap.put("laowang", set2);
        //================================
        Set<String> set3 = new HashSet<>();
        Set<String> set4 = new HashSet<>();
        set3.add("role1");
        set3.add("role2");
        set4.add("root");
        roleMap.put("laochen", set3);
        roleMap.put("laowang", set4);
    }

    /**
     * 授权认证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权 AuthorizationInfo");
        String name = (String) principals.getPrimaryPrincipal();
        //权限
        Set<String> permissions = getPermissionsByNameFromDB(name);
        //角色
        Set<String> roles = getRoleByNameFromDB(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 模拟从数据库中取角色
     *
     * @param name
     * @return
     */
    private Set<String> getRoleByNameFromDB(String name) {
        return roleMap.get(name);
    }

    /**
     * 模拟从数据库中取权限
     *
     * @param name
     * @return
     */
    private Set<String> getPermissionsByNameFromDB(String name) {
        return permissionMap.get(name);
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证 doGetAuthenticationInfo");
        //用户名
        String name = (String) token.getPrincipal();
        //从DB中根据用户取密码
        String pwd = getPwdByUserNameFromDB(name);
        if (pwd == null || "".equals(pwd)) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟从数据库中取密码
     *
     * @param name
     * @return
     */
    private String getPwdByUserNameFromDB(String name) {
        return userInfoMap.get(name);
    }
}
