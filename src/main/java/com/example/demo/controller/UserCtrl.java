package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    /**
     * 需要登录
     *
     * @return
     */
    @GetMapping("need_login")
    public String needLogin() {
        return "温馨提示：请使用对应的账号登录";
    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody User user) {
        Map returnMap = new HashMap<>();
        //拿到主体
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(usernamePasswordToken);
            returnMap.put("permissions", subject.getSession().getAttribute("permissions"));
            returnMap.put("token", subject.getSession().getId());
            return returnMap;
        }catch (Exception e){
            e.printStackTrace();
            returnMap.put("error", e.getMessage());
            return returnMap;
        }
    }

    /**
     * 获取用户所有权限
     */
    @RequestMapping(value = "/getPermissionName", method = RequestMethod.GET)
    @ResponseBody
    public Object getPermissionName() {
        Subject subject = SecurityUtils.getSubject();
        SecurityUtils.getSubject().getPrincipal();
        return null;
    }


    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Integer delete(Integer userId) {
        System.out.println(userId);
        int result = userService.delete(userId);
        return result;
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(User user) {
        int result = userService.modify(user);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(User user) {
        userService.insertUser(user);
    }

    /**
     * 查询全部用户
     *
     * @return
     */
    @RequestMapping("/listUser")
    @ResponseBody
    public List<User> listUser() {
        return userService.listUser();
    }

    /**
     * 通过用户名称查询用户
     *
     * @param userName
     * @return
     */
    @RequestMapping("/listByName")
    @ResponseBody
    public List<User> listByName(String userName) {
        return userService.findByName(userName);
    }

    /**
     * 分页
     *
     * @return
     */
    @RequestMapping(value="/page")
    @ResponseBody
    public List<User> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 5;
        int startRows = pageSize*(pageNow-1);
        List<User> list = userService.queryPage(startRows);
        return list;
    }

    /**
     * 获取用户数量
     *
     * @return
     */
    @RequestMapping(value="/rows")
    @ResponseBody
    public int rows(){
        return userService.getRowCount();
    }
}
