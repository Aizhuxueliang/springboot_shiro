package com.shiroTest.demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * @Description：
 * @Author：
 * @Date：2021/1/3 10:12 下午
 * @Versiion：1.0
 */
public class Md5Test {
    @Test
    public void testMd5(){
        String hashName="md5";
        String pwd="123";
        SimpleHash simpleHash = new SimpleHash(hashName, pwd, null, 2);
        System.out.println(simpleHash);
    }
}
