package com.shiroTest.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description：
 * @Author：
 * @Date：
 * @Versiion：1.0
 */
public class QuickStartTest {
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();

    @Before
    public void init() {
        //初始化数据源，模拟从数据库中取的数据
        accountRealm.addAccount("laochen", "123");
        accountRealm.addAccount("laowang", "123456");
        //构建环境
        defaultSecurityManager.setRealm(accountRealm);
    }

    @Test
    public void testAuthentication() {
        //设置上下文
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //获取当前主体
        Subject subject = SecurityUtils.getSubject();
        //模拟用户登录，账户、密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("laowang", "123456");
        subject.login(usernamePasswordToken);
        //判断是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("认证结果：" + authenticated);
    }

}