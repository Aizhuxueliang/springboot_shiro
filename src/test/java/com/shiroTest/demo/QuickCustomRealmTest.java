package com.shiroTest.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description：自定义realm
 * @Author：
 * @Date：2020/12/28 8:43 下午
 * @Versiion：1.0
 */
public class QuickCustomRealmTest {
    private CustomRealm customRealm=new CustomRealm();
    private DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();

    @Before
    public void init() {
        //构建环境
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testAuthentication(){
        //获取当前操作的主体
        Subject subject = SecurityUtils.getSubject();
        //用户输入账号、密码
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("laochen","123");
        subject.login(usernamePasswordToken);
        System.out.println("认证结果："+subject.isAuthenticated());
        //拿到主体标识属性
        System.out.println("获取subject名："+subject.getPrincipal());
        //是否有role1角色，没有则报错
        subject.checkRole("role1");
        //是否有对应的角色
        System.out.println("是否有对应的角色："+subject.hasRole("role1"));
        //是否有对应的权限
        System.out.println("是否有对应的权限："+subject.isPermitted("video:find"));
    }
}