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
 * 控制层
 */
@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User user) {
        //拿到主体
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(usernamePasswordToken);
            Object permissions = subject.getSession().getAttribute("permissions");
            subject.getSession().removeAttribute("permissions");
            return userService.resultMap("permissions", permissions, "token",  subject.getSession().getId(), "", "");
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 获取用户的角色类型
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/findRoleListByUserId", method = RequestMethod.POST)
    public Map<String, Object> findRoleListByUserId(@RequestBody User user) {
        try{
            return userService.findRoleListByUserId(user.getId());
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
    public Map<String, Object> findPermissionListByRoleId(@RequestBody Role role) {
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
    public Map<String, Object> updateRolePermission(@RequestBody Role role) {
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
    public Map<String, Object> removeRole(@RequestBody Role role) {
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
    public Map<String, Object> addRole(@RequestBody Role role) {
        try{
            return userService.addRole(role);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 更新用户具有的角色
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    public Map<String, Object> updateUserRole(@RequestBody User user) {
        try{
            return userService.updateUserRole(user);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 根据用户提供的条件分页查询用户
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/queryUserListPage", method = RequestMethod.POST)
    public Map<String, Object> queryUserListPage(@RequestBody User user) {
        try{
            return userService.queryUserListPage(user);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 删除用户
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    public Map<String, Object> removeUser(@RequestBody User user) {
        try{
            return userService.removeUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

    /**
     * 新增用户
     *
     * @param user user
     * @return resultMap
     */
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    public Map<String, Object> insertUser(@RequestBody User user) {
        try{
            return userService.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return userService.resultMap("error", e.getMessage(), "",  "", "", "");
        }
    }

}
