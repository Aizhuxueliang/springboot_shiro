package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制层
 */
@RestController
@SuppressWarnings({"rawtypes"})
@RequestMapping("user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    /**
     * 需要登录
     *
     * @return resultMap
     */
    @GetMapping("/need_login")
    public Map needLogin() {
        return userService.resultMap("error", "温馨提示：请使用对应的账号登录", "",  "", "", "");
    }

    /**
     * 登录接口
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestBody User user) {
        //拿到主体
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(usernamePasswordToken);
            return userService.resultMap("permissions", subject.getSession().getAttribute("permissions"), "token",  subject.getSession().getId(), "", "");
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 获取角色的权限类型
     *
     * @param role role
     * @return resultMap
     */
    @RequestMapping(value = "/findPermissionListByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public Map findPermissionListByRoleId(@RequestBody Role role) {
        try{
            return userService.findPermissionListByRoleId(role.getId());
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 更新角色具有的权限
     *
     * @param role role
     * @return resultMap
     */
    @RequestMapping(value = "/updateRolePermission", method = RequestMethod.POST)
    @ResponseBody
    public Map updateRolePermission(@RequestBody Role role) {
        try{
            return userService.updateRolePermission(role);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 删除角色
     *
     * @param role role
     * @return resultMap
     */
    @RequestMapping(value = "/removeRole", method = RequestMethod.POST)
    @ResponseBody
    public Map removeRole(@RequestBody Role role) {
        try{
            return userService.removeRole(role);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 添加角色
     *
     * @param role role
     * @return resultMap
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public Map addRole(@RequestBody Role role) {
        try{
            return userService.addRole(role);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }


    /**
     * 新增用户
     *
     * @param user role
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(User user) {
        userService.insertUser(user);
    }

}
