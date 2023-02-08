package com.example.demo.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfig ShiroFilterFactoryBean 执行");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果访问需要登录的某个接口，却没有登录，则调用此接口(如果不是前后端分离，则跳转页面)
        //shiroFilterFactoryBean.setLoginUrl("/index.html");
        //登录成功后，跳转的链接，若前后端分离，没必要设置这个
        //shiroFilterFactoryBean.setSuccessUrl("");
        //登录成功，未授权会调用此方法
        //shiroFilterFactoryBean.setUnauthorizedUrl("/user/*");
        //拦截路径，必须使用:LinkedHashMap，要不然拦截效果会时有时无，因为使用的是无序的Map
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //key=正则表达式路径，value=org.apache.shiro.web.filter.mgt.DefaultFilter
        //退出过滤器
        //filterChainDefinitionMap.put("/logout", "logout");
        //匿名可以访问，游客模式
        filterChainDefinitionMap.put("/user/login", "anon");
        //登录用户才可以访问
        filterChainDefinitionMap.put("/user/**", "authc");
        //管理员角色才能访问
        //filterChainDefinitionMap.put("/user/**", "roles[admin]");
        //有权限才能访问
        filterChainDefinitionMap.put("/user/queryUserListPage", "perms[query_user, add_user, allot_roles, remove_user]");
        filterChainDefinitionMap.put("/user/insertUser", "perms[add_user]");
        filterChainDefinitionMap.put("/user/removeUser", "perms[remove_user]");
        filterChainDefinitionMap.put("/user/findRoleListByUserId", "perms[allot_roles, query_role, add_role, remove_role, allot_permission]");
        filterChainDefinitionMap.put("/user/updateUserRole", "perms[allot_roles]");
        filterChainDefinitionMap.put("/user/addRole", "perms[add_role]");
        filterChainDefinitionMap.put("/user/removeRole", "perms[remove_role]");
        filterChainDefinitionMap.put("/user/findPermissionListByRoleId", "perms[allot_permission]");
        filterChainDefinitionMap.put("/user/updateRolePermission", "perms[allot_permission]");
        //authc：url必须通过认证才可以访问
        //anon：url可以匿名访问
        //过滤链是顺序执行，从上而下，一般把/**，放到最下面
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //如果不是前后端分离，不用设置setSessionManager
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 自定义realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //因为数据库密码存的是明文，所以无需使用双重md5校验
//        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 密码验证器，双重md5
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法，使用md5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列次数，使用2次md5算法，相当于md5(md5(xxx))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义SessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        UserSessionManager userSessionManager = new UserSessionManager();
        //超时时间，默认 30分钟，会话超时，单位毫秒
//        customSessionManager.setGlobalSessionTimeout(200000);
        return userSessionManager;
    }
}
