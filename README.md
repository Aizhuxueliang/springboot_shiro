## shiro、springboot、vue、elementUI CDN模式前后端分离的权限管理demo 附源码
### 源码下载地址
[https://github.com/Aizhuxueliang/springboot_shiro.git](https://github.com/Aizhuxueliang/springboot_shiro.git)
![open](https://img-blog.csdnimg.cn/7d54e86131604ee9ba1af0f30aa0239f.png#pic_center)

前提你电脑的安装好这些工具：`jdk8、idea、maven、git、mysql`；
### shiro的主要概念
1. Shiro是一个强大的简单易用的Java安全框架，主要用来更便捷的认证、授权、加密、会话管理、与Web集成、缓存等；
2. Shiro使用起来小而简单；
3. spring中有spring security ,是一个权限框架，它和spring依赖过于紧密，没有shiro使用简单；
4. shiro不依赖于spring,shiro不仅可以实现web应用的权限管理，还可以实现c/s系统，分布式系统权限管理；
### 在应用程序角度来观察如何使用Shiro完成工作
![subject](https://img-blog.csdnimg.cn/a8f62401b81a4b4da78fd51a97075850.png#pic_center)

**Subject**：主体，代表了当前“用户”，这个用户不一定是一个具体的人，与当前应用交互的任何东西都是Subject，如网络爬虫，机器人等；即一个抽象概念；所有Subject 都绑定到SecurityManager，与Subject的所有交互都会委托给SecurityManager；可以把Subject认为是一个门面；SecurityManager才是实际的执行者；

**SecurityManager**：安全管理器；即所有与安全有关的操作都会与SecurityManager 交互；且它管理着所有Subject；可以看出它是Shiro 的核心，它负责与后边介绍的其他组件进行交互，如果学习过SpringMVC，你可以把它看成DispatcherServlet前端控制器；

**Realm**：域，Shiro从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
### shiro官方文档
[https://shiro.apache.org/architecture.html](https://shiro.apache.org/architecture.html)
### 前端CDN的方式使用elementUI、vue、vue-router、axios画页面
CDN（内容分发网络）本身是指一种请求资源的方式。说白了就是在本地，通过script头去请求对应的脚本资源的一种方式。我在这里要说的就是直接引用 （`demo是直接引用的，注意电脑联网`）或者下载Vue.js和elementUI.js放在本地，进行项目开发的方式。而不是通过npm包管理工具去下载vue包。

cdn方式引入elementui 官方API：[https://element.eleme.cn/#/zh-CN/component/installation](https://element.eleme.cn/#/zh-CN/component/installation)
![在这里插入图片描述](https://img-blog.csdnimg.cn/f6793dce3efb48019c0ab5b527e78190.png#pic_center)
cdn方式引入vue-router官方API：[https://router.vuejs.org/zh/guide/#html](https://router.vuejs.org/zh/guide/#html)
![router](https://img-blog.csdnimg.cn/f3a31c6749e94e71a904bebdbf90c1b8.png#pic_center)

### DEMO的整体流程设计
技术层面了解完就该设计业务流程了，如图：
![process](https://img-blog.csdnimg.cn/82e87b25ccd14c7e9fd6cd72b9676642.png#pic_center)

### demo整体结构
如图：
![struct](https://img-blog.csdnimg.cn/9fd0f08a24f64bd2a94609255b5d1af4.png#pic_center)

### 主要功能页面
1、登录
![login](https://img-blog.csdnimg.cn/5c417034f2004521a8417125eece9f5f.png#pic_center)

2、用户查询
![query_user](https://img-blog.csdnimg.cn/54101c31a06f4a84ab3548437d7c401c.png#pic_center)

3、分配角色
![allot_role](https://img-blog.csdnimg.cn/595e2e9e94d44446b8a303ec1e5d16ad.png#pic_center)

4、删除角色
![remove_role](https://img-blog.csdnimg.cn/de203a79b88c428c932544802c3d2669.png#pic_center)

5、新建用户
![create_user](https://img-blog.csdnimg.cn/bba74b41956c468ead55d66d468e6b74.png#pic_center)
![create_user_1](https://img-blog.csdnimg.cn/a6881aba548c41a289e77283fa7d6489.png#pic_center)

6、分配权限
![allot_permission](https://img-blog.csdnimg.cn/2eb7b25bcaa24225964b4796cd80183a.png#pic_center)

7、新建角色
![create_role](https://img-blog.csdnimg.cn/0a4fc4f7dcec46a1b049c369ac853ffc.png#pic_center)

### 主要代码
com/example/demo/controller/UserCtrl.java

```java
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
```

com/example/demo/service/UserService.java

```java
package com.example.demo.service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.RoleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务层
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        //用户角色的集合
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    public Map<String, Object> findRoleListByUserId(int userId){
        //用戶具有的角色集合
        List<Role> beRoleList = roleMapper.findRoleListByUserIdNotPermission(userId);
        //用戶没有的角色集合
        List<Role> notRoleList = roleMapper.findNotRoleListByUserIdNotPermission(userId);
        //所有角色集合
        Collection<?> allRoleList = CollectionUtils.union(beRoleList, notRoleList);
        return this.resultMap("beRoleList", beRoleList, "notRoleList", notRoleList, "allRoleList", allRoleList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> updateUserRole(User user){
        int removeUserRole = userMapper.removeUserRoleByUserId(user.getId());
        int addUserRole = 0;
        if (user.getRoleList().size()!=0 && user.getRoleList()!=null){
            addUserRole = userMapper.addUserRole(user);
        }
        return this.resultMap("removeUserRole", removeUserRole, "addUserRole", addUserRole, "", "");
    }

    public Map<String, Object> findPermissionListByRoleId(int roleId){
        //角色具有的权限集合
        List<Permission> bePermissionList = permissionMapper.findByPermissionListByRoleId(roleId);
        //角色没有的权限集合
        List<Permission> notPermissionList = permissionMapper.findNotPermissionListByRoleId(roleId);
        //所有权限集合
        Collection<?> allPermissionList = CollectionUtils.union(bePermissionList, notPermissionList);
        return this.resultMap("bePermissionList", bePermissionList, "notPermissionList", notPermissionList, "allPermissionList", allPermissionList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> updateRolePermission(Role role){
        int removeRolePermission = roleMapper.removeRolePermissionByRoleId(role.getId());
        int addRolePermission = 0;
        if (role.getPermissionList().size()!=0 && role.getPermissionList()!=null){
            addRolePermission = roleMapper.addRolePermission(role);
        }
        return this.resultMap("removeRolePermission", removeRolePermission, "addRolePermission", addRolePermission, "", "");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> removeRole(Role role){
        int removeRolePermission = roleMapper.removeRolePermissionByRoleId(role.getId());
        int removeRole = roleMapper.removeRoleByRoleId(role.getId());
        int removeUserRole = userMapper.removeUserRoleByRoleId(role.getId());
        return this.resultMap("removeRolePermission", removeRolePermission, "removeRole", removeRole, "removeUserRole", removeUserRole);
    }

    public Map<String, Object> queryUserListPage(User user){
        //当前页页码
        int pageNow = user.getReserve1() < 1 ? 1 : user.getReserve1();
        //当前页第一行索引
        user.setReserve1(5*(pageNow - 1));
        List<User> userListPage = userMapper.queryUserListPage(user);
        int userRowCount = userMapper.getUserRowCount(user);
        return this.resultMap("userListPage", userListPage, "userRowCount",  userRowCount, "", "");
    }

    public Map<String, Object> addRole(Role role){
        int addRole = roleMapper.addRole(role);
        return this.resultMap("addRole", addRole, "",  "", "", "");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> removeUser(User user){
        int removeUser = userMapper.removeUserByUserId(user.getId());
        int removeUserRole = userMapper.removeUserRoleByUserId(user.getId());
        return this.resultMap("removeUser", removeUser, "removeUserRole", removeUserRole, "", "");
    }

    public Map<String, Object> insertUser(User user) {
        int addUser = userMapper.insertUser(user);
        return this.resultMap("addUser", addUser, "", "", "", "");
    }

    public Map<String, Object> resultMap(String str1, Object obj1, String str2, Object obj2, String str3, Object obj3){
        Map<String, Object> resultMap = new HashMap<>();
        if (!"".equals(str1) || !"".equals(obj1))
            resultMap.put(str1, obj1);
        if (!"".equals(str2) || !"".equals(obj2))
            resultMap.put(str2, obj2);
        if (!"".equals(str3) || !"".equals(obj3))
            resultMap.put(str3, obj3);
        return resultMap;
    }

}
```

### CDN模式下的vue、vue-router、template模板挂载操作
参考：src/main/resources/static/index.html

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- import CSS -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
</head>
<body>
<div id="app">
    <router-view></router-view>
</div>
<template id="sign">
    <div class="handle">
        <div class="handle-input">
            <div class="high70">
                <span>用户名称:</span>
                <el-input
                        v-model="username"
                        placeholder="请输入用户名称"
                        clearable></el-input>
            </div>
            <div class="high70">
                <span>密码:</span>
                <el-input
                        v-model="password"
                        placeholder="请输入密码"
                        clearable
                        show-password></el-input>
            </div>
            <el-button @click="login" plain>登录</el-button>
        </div>
    </div>
</template>
<template id="manager">
    <el-tabs :tab-position="tabPosition">
        <el-tab-pane label="用户设置" v-if="permissionsFlag.query_user || permissionsFlag.add_user || permissionsFlag.allot_roles || permissionsFlag.remove_user">
            <el-row type="flex" justify="center">
                <el-col :span="18">
                    <div class="handle-area">
                        <div class="demo-input" v-show="permissionsFlag.query_user || permissionsFlag.add_user || permissionsFlag.allot_roles || permissionsFlag.remove_user">
                            <span>用户ID:</span>
                            <el-input
                                    v-model="id"
                                    placeholder="请输入用户ID"
                                    type="number"
                                    size="medium"
                                    clearable></el-input>
                            <span>用户名称:</span>
                            <el-input
                                    v-model="username"
                                    placeholder="请输入用户名称"
                                    size="medium"
                                    clearable></el-input>
                            <el-button
                                    :plain="true"
                                    @click="queryUserList"
                                    icon="el-icon-search"
                                    size="medium">查询用户</el-button>
                        </div>
                        <el-button
                                v-show="permissionsFlag.add_user"
                                :plain="true"
                                @click="handleAddUser"
                                icon="el-icon-user"
                                size="medium">新建用户</el-button>
                    </div>
                    <el-table
                            :data="tableData"
                            border
                            highlight-current-row>
                        <el-table-column
                                prop="id"
                                label="用户ID">
                        </el-table-column>
                        <el-table-column
                                prop="username"
                                label="用户名称">
                        </el-table-column>
                        <el-table-column
                                label="操作">
                            <template slot-scope="scope">
                                <el-button
                                        v-show="permissionsFlag.allot_roles"
                                        type="text"
                                        icon="el-icon-set-up"
                                        @click="handleEditUser(scope.$index, scope.row)">分配角色
                                </el-button>
                                <el-button
                                        v-show="permissionsFlag.remove_user"
                                        type="text"
                                        icon="el-icon-remove-outline"
                                        @click="handleDeleteUser(scope.$index, scope.row)">删除用户
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            layout="total, prev, pager, next, jumper"
                            :total="userRowCount"
                            :page-size="5"
                            :current-page.sync="currentPage"
                            @current-change="queryUserListPage"
                            :hide-on-single-page="hidePageFlag"
                            background>
                    </el-pagination>
                </el-col>
            </el-row>
            <el-dialog :title="roleTitle" :visible.sync="dialogEditUser" :before-close="handleClose">
                <el-transfer
                        v-model="transferValue"
                        :data="transferData"
                        :titles="['待分配角色', '已分配角色']">
                </el-transfer>
                <span slot="footer" class="dialog-footer">
			        <el-button @click="handleClose">取 消</el-button>
			        <el-button type="primary" @click="assignRoles">确 定</el-button>
		        </span>
            </el-dialog>
            <el-dialog title="新建用户" :visible.sync="dialogAddUser" :before-close="handleClose">
                <div class="high70">
                    <span>用户名称:</span>
                    <el-input
                            v-model="username"
                            placeholder="请输入用户名称"
                            size="medium"
                            clearable></el-input>
                </div>
                <div class="high70">
                    <span>密码:</span>
                    <el-input
                            v-model="password"
                            placeholder="请输入密码"
                            size="medium"
                            clearable
                            show-password></el-input>
                </div>
                <div class="high70">
                    <span>再次输入密码:</span>
                    <el-input
                            v-model="password1"
                            placeholder="请再次输入密码"
                            size="medium"
                            show-password></el-input>
                </div>
                <div class="high70">
                    <span>备注:</span>
                    <el-input
                            v-model="reserve"
                            placeholder="请输入用户备注"
                            size="medium"></el-input>
                </div>
                <span slot="footer" class="dialog-footer">
			        <el-button @click="handleClose">取 消</el-button>
			        <el-button type="primary" @click="insertUser">确 定</el-button>
		        </span>
            </el-dialog>
        </el-tab-pane>

        <el-tab-pane label="角色设置" v-if="permissionsFlag.allot_roles || permissionsFlag.query_role || permissionsFlag.add_role || permissionsFlag.remove_role || permissionsFlag.allot_permission">
            <el-row type="flex" justify="center">
                <el-col :span="18">
                    <div class="handle-area">
                        <el-button
                                v-show="permissionsFlag.allot_roles || permissionsFlag.query_role || permissionsFlag.add_role || permissionsFlag.remove_role || permissionsFlag.allot_permission"
                                :plain="true"
                                @click="queryRoleList"
                                icon="el-icon-search"
                                size="medium">角色查询</el-button>
                        <el-button
                                v-show="permissionsFlag.add_role"
                                :plain="true"
                                @click="handleAddRole"
                                icon="el-icon-circle-plus-outline"
                                size="medium">新建角色</el-button>
                    </div>
                    <el-table
                            :data="roleData"
                            border
                            highlight-current-row>
                        <el-table-column
                                prop="id"
                                label="角色ID">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="角色名称">
                        </el-table-column>
                        <el-table-column
                                prop="description"
                                label="角色描述">
                        </el-table-column>
                        <el-table-column
                                label="操作">
                            <template slot-scope="scope">
                                <el-button
                                        v-show="permissionsFlag.allot_permission"
                                        type="text"
                                        icon="el-icon-s-operation"
                                        @click="handleEditRole(scope.$index, scope.row)">分配权限
                                </el-button>
                                <el-button
                                        v-show="permissionsFlag.remove_role"
                                        type="text"
                                        icon="el-icon-remove-outline"
                                        @click="handleDeleteRole(scope.$index, scope.row)">删除角色
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <el-dialog :title="permissionsTitle" :visible.sync="dialogEditRole" :before-close="handleClose">
                <el-checkbox-group v-model="checkedPermissions" class="el-checkbox-group-dialog">
                    <el-checkbox v-for="permission in permissions" :label="permission.name" :key="permission.name">{{permission.description}}</el-checkbox>
                </el-checkbox-group>
                <span slot="footer" class="dialog-footer">
			        <el-button @click="handleClose">取 消</el-button>
			        <el-button type="primary" @click="assignPermissions">确 定</el-button>
		        </span>
            </el-dialog>
            <el-dialog title="新建角色" :visible.sync="dialogAddRole" :before-close="handleClose">
                <div class="high70">
                    <span>角色名称:</span>
                    <el-input
                            v-model="name"
                            placeholder="请输入角色名称"
                            size="medium"></el-input>
                </div>
                <div class="high70">
                    <span>角色描述:</span>
                    <el-input
                            v-model="description"
                            placeholder="请输入角色描述"
                            size="medium"></el-input>
                </div>
                <span slot="footer" class="dialog-footer">
			        <el-button @click="handleClose">取 消</el-button>
			        <el-button type="primary" @click="insertRole">确 定</el-button>
		        </span>
            </el-dialog>
        </el-tab-pane>

        <el-tab-pane label="权限列表" v-if="permissionsFlag.allot_permission">
            <el-row type="flex" justify="center">
                <el-col :span="18">
                    <div class="handle-area">
                        <el-button
                                v-show="permissionsFlag.allot_permission"
                                :plain="true"
                                @click="queryPermissionList"
                                icon="el-icon-search"
                                size="medium">权限查询</el-button>
                    </div>
                    <el-table
                            :data="permissionData"
                            border
                            highlight-current-row>
                        <el-table-column
                                prop="id"
                                label="权限ID">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="权限名称">
                        </el-table-column>
                        <el-table-column
                                prop="description"
                                label="权限描述">
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </el-tab-pane>
    </el-tabs>
</template>
</body>
<!-- import Vue before Element -->
<script src="https://unpkg.com/vue@2/dist/vue.js"></script>
<!-- import JavaScript -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<!-- import Axios -->
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<!-- import Router -->
<script src="https://cdn.staticfile.org/vue-router/2.7.0/vue-router.min.js"></script>
<script>
    const Sign = {
        props: ['todo'],
        template: '#sign',
        data() {
            return {
                username: '',
                password: '',
                permissionsStr: [],
                token: ''
            }
        },
        methods: {
            login() {
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/login',
                    data: JSON.stringify({
                        username: this.username,
                        password: this.password
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        this.username = '';
                        this.password = '';
                        ELEMENT.Notification.error({
                            title: '登录失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    this.permissionsStr = response.data.permissions;
                    this.token = response.data.token;
                    this.$router.push({
                        name: 'manager',
                        params: {
                            token: this.token,
                            permissionsStr: this.permissionsStr
                        }
                    });
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            }
        },
        mounted() {
            console.log('组件Sign被挂载了');
        }
    };
    const Manager = {
        // todo-item 组件现在接受一个
        // "prop"，类似于一个自定义 attribute。
        // 这个 prop 名为 todo。
        props: ['todo'],
        template: '#manager',
        data() {
            return {
                // 角色信息
                roleData: [],
                roleId: null,
                name: '',
                description: '',
                dialogEditRole: false,
                dialogAddRole: false,
                permissions: [],
                checkedPermissions: [],
                permissionList: [],
                permissionsTitle: '',
                // 权限信息
                permissionData: [],
                // 穿梭框
                transferValue: [],
                transferData: [],
                // 表格及分页
                tableData: [],
                tabPosition: 'top',
                userRowCount: 0,
                currentPage: 0,
                hidePageFlag: true,
                // 弹框
                dialogEditUser: false,// 分配角色弹框
                dialogAddUser: false,// 新建用户弹框
                // 用户信息
                allRoleList: [],
                roleList: [],
                id: null,
                username: '',
                password: '',
                password1: '',
                reserve: '',
                roleTitle: '',
                permissionsStr: [],
                permissionsFlag: {
                    query_user: false,
                    add_user: false,
                    remove_user: false,
                    allot_roles: false,
                    query_role: false,
                    add_role: false,
                    remove_role: false,
                    allot_permission: false
                },
                token: ''

            }
        },
        methods: {
            queryPermissionList() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/findPermissionListByRoleId',
                    data: JSON.stringify({
                        id: 0
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    this.permissionData = response.data.allPermissionList;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            queryRoleList() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/findRoleListByUserId',
                    data: JSON.stringify({
                        id: 0
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    this.roleData = response.data.allRoleList;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });

            },
            handleEditRole(index, row){
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/findPermissionListByRoleId',
                    data: JSON.stringify({
                        id: row.id
                    })
                }).then(response => {
                    console.log(response.data);
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    this.permissions = response.data.allPermissionList;
                    for(let item of response.data.bePermissionList){
                        this.checkedPermissions.push(item.name);
                    }
                    this.roleId = row.id;
                    this.name = row.name;
                    this.permissionsTitle = '为角色'+row.name+'分配权限';
                    this.dialogEditRole = true;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            handleDeleteRole(index, row){
                ELEMENT.MessageBox.confirm('此操作将永久删除角色'+row.name+', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.defaults.headers.token = this.token;
                    axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                    axios({
                        method: 'post',
                        url: '/user/removeRole',
                        data: JSON.stringify({
                            id: row.id
                        })
                    }).then(response => {
                        if(typeof response.data.error !== 'undefined' || response.data.error != null){
                            ELEMENT.Notification.error({
                                title: '角色'+row.name+'，删除失败！',
                                message: response.data.error,
                                position: 'top-right',
                                showClose: false,
                                offset: 110
                            });
                            return;
                        }
                        if (response.data.removeRolePermission >= 0 && response.data.removeRole >= 0 && response.data.removeUserRole >= 0) {
                            this.queryRoleList();
                            ELEMENT.Notification({
                                title: '删除成功',
                                message: '角色'+row.name+'，删除成功！',
                                type: 'success',
                                position: 'top-right',
                                showClose: false,
                                offset: 110
                            });
                        }
                    }).catch(error => {
                        ELEMENT.Message({ type: 'info', message: error});
                    });
                }).catch(() => {
                    ELEMENT.Message({ type: 'info', message: '已取消删除'});
                });
            },
            handleAddRole(){
                this.dialogAddRole = true;
                this.name = '';
                this.description = '';
            },
            insertRole(){
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/addRole',
                    data: JSON.stringify({
                        name: this.name,
                        description: this.description
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '角色'+this.name+'，添加失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.dialogAddRole = false;
                        this.name = '';
                        this.description = '';
                        return;
                    }
                    if(response.data.addRole > 0) {
                        this.queryRoleList();
                        ELEMENT.Notification({
                            title: '添加成功',
                            message: '角色'+this.name+'，添加成功！',
                            type: 'success',
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.dialogAddRole = false;
                        this.name = '';
                        this.description = '';
                    }
                    console.log(response.data);
                }).catch(error => {
                    ELEMENT.Message({ type: 'info', message: error});
                });
            },
            assignPermissions(){
                for(let item of this.permissions){
                    for(let i in this.checkedPermissions){
                        if (this.checkedPermissions[i] == item.name) {
                            this.permissionList.push(item);
                        }
                    }
                }
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/updateRolePermission',
                    data: JSON.stringify({
                        id: this.roleId,
                        permissionList: this.permissionList,
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        this.dialogEditRole = false;
                        ELEMENT.Notification.error({
                            title: '为角色'+this.name+'，分配权限失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.roleId = null;
                        this.name = null;
                        this.permissions = [];
                        this.checkedPermissions = [];
                        this.permissionList = [];
                        this.permissionsTitle = '';
                        return;
                    }
                    if (response.data.removeRolePermission >= 0 && response.data.addRolePermission >= 0) {
                        this.dialogEditRole = false;
                        ELEMENT.Notification({
                            title: '更新成功',
                            message: '为角色'+this.name+'，分配权限成功！',
                            type: 'success',
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.roleId = null;
                        this.name = null;
                        this.permissions = [];
                        this.checkedPermissions = [];
                        this.permissionList = [];
                        this.permissionsTitle = '';
                    }
                    console.log(response.data);
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            // ------------------------用户设置部分--------------
            queryUserList() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/queryUserListPage',
                    data: JSON.stringify({
                        id: this.id,
                        username: this.username
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询用户失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    console.log(response.data);
                    this.tableData = response.data.userListPage;
                    this.userRowCount = response.data.userRowCount;
                    if(this.userRowCount > 5) {
                        this.hidePageFlag = false;
                    } else {
                        this.hidePageFlag = true;
                    }
                    this.currentPage = 1;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            queryUserListPage() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/queryUserListPage',
                    data: JSON.stringify({
                        id: this.id,
                        username: this.username,
                        reserve1: this.currentPage
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询用户失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    console.log(response.data);
                    this.tableData = response.data.userListPage;
                    this.userRowCount = response.data.userRowCount;
                    if(this.userRowCount > 5) {
                        this.hidePageFlag = false;
                    } else {
                        this.hidePageFlag = true;
                    }
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            handleEditUser(index, row) {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/findRoleListByUserId',
                    data: JSON.stringify({
                        id: row.id
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        this.dialogEditUser = false;
                        ELEMENT.Notification.error({
                            title: '为用户'+row.username+'分配角色失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    console.log(response.data);
                    for(let item of response.data.allRoleList){
                        this.transferData.push({
                            key: item.id,
                            label: item.description
                        });
                    }
                    for(let item of response.data.beRoleList){
                        this.transferValue.push(item.id);
                    }
                    this.id = row.id;
                    this.username = row.username;
                    this.allRoleList = response.data.allRoleList;
                    this.roleTitle = '为用户'+row.username+'分配角色';
                    this.dialogEditUser = true;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
            assignRoles() {
                for(let item of this.allRoleList){
                    for(let i in this.transferValue){
                        if (this.transferValue[i] == item.id) {
                            this.roleList.push(item);
                        }
                    }
                }
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/updateUserRole',
                    data: JSON.stringify({
                        id: this.id,
                        username: this.username,
                        roleList: this.roleList
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        this.dialogEditUser = false;
                        ELEMENT.Notification.error({
                            title: '为用户'+row.username+'分配角色失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.id = null;
                        this.username = '';
                        this.allRoleList = [];
                        this.roleList = [];
                        this.transferData = [];
                        this.transferValue = [];
                        return;
                    }
                    if (response.data.removeUserRole >= 0 && response.data.addUserRole >= 0) {
                        this.dialogEditUser = false;
                        ELEMENT.Notification({
                            title: '更新成功',
                            message: '为用户'+this.username+'，分配角色成功！',
                            type: 'success',
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        this.id = null;
                        this.username = '';
                        this.allRoleList = [];
                        this.roleList = [];
                        this.transferData = [];
                        this.transferValue = [];
                    }
                    console.log(response.data);
                }).catch(error => {
                    console.log(error);
                });

            },
            handleDeleteUser(index, row) {
                //this.ruleForm = Object.assign({}, row, index); //这句是关键！！！
                ELEMENT.MessageBox.confirm('此操作将永久删除用户'+row.id+', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.defaults.headers.token = this.token;
                    axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                    axios({
                        method: 'post',
                        url: '/user/removeUser',
                        data: JSON.stringify({
                            id: row.id
                        })
                    }).then(response => {
                        if(typeof response.data.error !== 'undefined' || response.data.error != null){
                            ELEMENT.Notification.error({
                                title: '用户'+row.username+'，删除失败！',
                                message: response.data.error,
                                position: 'top-right',
                                showClose: false,
                                offset: 110
                            });
                            return;
                        }
                        if (response.data.removeUser >= 0 && response.data.removeUserRole >= 0) {
                            if(((this.userRowCount - 1) % 5 == 0) && ((this.userRowCount - 1) / 5 == (this.currentPage - 1))) {
                                this.currentPage = this.currentPage - 1;
                            }
                            this.queryUserListPage();
                            ELEMENT.Notification({
                                title: '删除成功',
                                message: '用户'+row.username+'，删除成功！',
                                type: 'success',
                                position: 'top-right',
                                showClose: false,
                                offset: 110
                            });
                        }
                    }).catch(error => {
                        ELEMENT.Message({ type: 'info', message: error});
                    });
                }).catch(() => {
                    ELEMENT.Message({ type: 'info', message: '已取消删除'});
                });
            },
            handleClose() {
                // 用户设置
                this.dialogEditUser = false;
                this.dialogAddUser = false;
                this.id = null;
                this.username = '';
                this.password = '';
                this.password1 = '';
                this.reserve = '';
                this.allRoleList = [];
                this.roleList = [];
                this.transferData = [];
                this.transferValue = [];
                this.roleTitle = '';
                // 角色设置
                this.dialogEditRole = false;
                this.dialogAddRole = false;
                this.roleId = null;
                this.name = null;
                this.permissions = [];
                this.checkedPermissions = [];
                this.permissionList = [];
                this.permissionsTitle = '';
                ELEMENT.Message({
                    message: '取消操作，系统不会保留任何更改',
                    type:'warning'
                });
            },
            handleAddUser(){
                this.dialogAddUser = true;
                this.id = null;
                this.username = '';
                this.password = '';
                this.password1 = '';
                this.reserve = '';
            },
            insertUser() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/insertUser',
                    data: JSON.stringify({
                        username: this.username,
                        password: this.password,
                        reserve: this.reserve
                    })
                }).then(response => {
                    if(response.data.addUser > 0) {
                        this.dialogAddUser = false;
                        this.password = '';
                        this.password1 = '';
                        this.reserve = '';
                        this.queryUserList();
                        ELEMENT.Notification({
                            title: '添加成功',
                            message: '用户'+this.username+'，添加成功！',
                            type: 'success',
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                    }
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        this.dialogAddUser = false;
                        this.password = '';
                        this.password1 = '';
                        this.reserve = '';
                        this.queryUserList();
                        ELEMENT.Notification.error({
                            title: '用户'+this.username+'，添加失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    console.log(response.data);
                }).catch(error => {
                    ELEMENT.Message({ type: 'info', message: error});
                });
            }
        },
        mounted() {
            console.log('组件Manager被挂载了');
        },
        created() {
            this.permissionsStr = this.$route.params.permissionsStr;
            this.token = this.$route.params.token;
            for(let key in this.permissionsFlag){
                for(let i in this.permissionsStr){
                    if(this.permissionsStr[i] == key){
                        this.permissionsFlag[key] = true;
                    }
                }
                console.log(key + '---' + this.permissionsFlag[key])
            }
        }
    };
    const router = new VueRouter({
        routes:[
            {
                path: '/',
                name: 'sign',
                component: Sign
            },
            {
                path: '/manager',
                name: 'manager',
                component: Manager
            }
        ]
    });
    new Vue({
        router,
        el: '#app',
        data: {},
        methods: {}
    })
</script>
<style>
    .el-pagination {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        height: 60px;
    }
    .el-transfer {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .handle-area {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 70px;
        flex-wrap: nowrap;
    }
    .demo-input {
        display: flex;
        align-items: center;
    }
    .demo-input span {
        white-space: nowrap;
    }
    .el-dialog__body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        flex-wrap: nowrap;
        height: 280px;
    }
    .el-checkbox-group-dialog {
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin: auto;
    }
    .el-checkbox {
        height: 30px;
        line-height: 30px;
    }
    .high70 {
        height: 70px;
    }
    .handle {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        margin: 0;
        background-image: url(banner.jpg);
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .handle-input {
        display: flex;
        flex-direction: column;
    }
</style>
</html>
```

具体看index.html 代码，如果没看懂，参考网址：
[https://blog.csdn.net/laow1314/article/details/109323527](https://blog.csdn.net/laow1314/article/details/109323527)
[https://blog.csdn.net/qq_29722281/article/details/85016524](https://blog.csdn.net/qq_29722281/article/details/85016524)
### 权限相关表
最基础的CRUD操作无法体现shiro的功能，需引入RBAC （Role-Based Access Control）思想，配置至少需要三张主表分别代表用户（user）、角色（role）、权限（permission），及两个附表分别是用户和角色（user_role），角色与权限(role_permission)表，表结构如下：
![ER](https://img-blog.csdnimg.cn/de156ecd85344fa1acea46a0e5b233e2.png#pic_center)

对应的实体如下：
1、com/example/demo/entity/User.java

```java
package com.example.demo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 主键
     */
    private int id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 保留字段
     */
    private String reserve;

    /**
     * 保留字段1
     */
    private int reserve1;
    /**
     * 角色集合
     */
    private List<Role> roleList = new ArrayList<>();

    public User(String json) throws IOException {
        User user = new ObjectMapper().readValue(json,User.class);
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.reserve = user.getReserve();
        this.reserve1 = user.getReserve1();
        this.roleList = user.getRoleList();
    }
}
```
2、com/example/demo/entity/Role.java

```java
package com.example.demo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * 主键
     */
    private int id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 权限集合
     */
    private List<Permission> permissionList=new ArrayList<>();

}
```
3、com/example/demo/entity/Permission.java

```java
package com.example.demo.entity;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    /**
     * 主键
     */
    private int id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限描述
     */
    private String description;

}
```
### 前后端分离项目中使用shiro的session鉴别用户身份
1、src/main/resources/static/index.html

```html
<template id="sign">
    <div class="handle">
        <div class="handle-input">
            <div class="high70">
                <span>用户名称:</span>
                <el-input
                        v-model="username"
                        placeholder="请输入用户名称"
                        clearable></el-input>
            </div>
            <div class="high70">
                <span>密码:</span>
                <el-input
                        v-model="password"
                        placeholder="请输入密码"
                        clearable
                        show-password></el-input>
            </div>
            <el-button @click="login" plain>登录</el-button>
        </div>
    </div>
</template>
```

```js
login() {
            axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
            axios({
                method: 'post',
                url: '/user/login',
                data: JSON.stringify({
                    username: this.username,
                    password: this.password
                })
            }).then(response => {
                if(typeof response.data.error !== 'undefined' || response.data.error != null){
                    this.username = '';
                    this.password = '';
                    ELEMENT.Notification.error({
                        title: '登录失败！',
                        message: response.data.error,
                        position: 'top-right',
                        showClose: false,
                        offset: 110
                    });
                    return;
                }
                this.permissionsStr = response.data.permissions;
                this.token = response.data.token;
                this.$router.push({
                    name: 'manager',
                    params: {
                        token: this.token,
                        permissionsStr: this.permissionsStr
                    }
                });
            }).catch(error => {
                console.log(error);
                ELEMENT.Message(error);
            });
        }
```
2、com/example/demo/controller/UserCtrl.java

```java
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
```
3、com/example/demo/config/UserRealm.java

```java
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
```
4、com/example/demo/service/UserService.java

```java
    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        //用户角色的集合
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }
```
5、src/main/resources/mapper/UserMapper.xml

```sql
    <resultMap id="resultUser" type="com.example.demo.entity.User">
        <result property="id" column="id" />
		<result property="username" column="username" />
		<result property="password" column="password" />
    </resultMap>

    <select id="findUserByName" resultMap="resultUser" parameterType="String">
		select
			id,
			username,
		    password
		from user where
		    username = #{userName}
	</select>
```
6、src/main/resources/mapper/RoleMapper.xml

```sql
    <resultMap id="roleResult" type="com.example.demo.entity.Role">
        <result property="id" column="id" />
		<result property="name" column="name" />
        <result property="description" column="description" />
		<collection property="permissionList" column="id" select="com.example.demo.mapper.PermissionMapper.findByPermissionListByRoleId" />
    </resultMap>

	<resultMap id="roleResultNotPermission" type="com.example.demo.entity.Role">
		<result property="id" column="id" />
		<result property="name" column="name" />
		<result property="description" column="description" />
	</resultMap>

    <select id="findRoleListByUserId" resultMap="roleResult" parameterType="int">
		select
			r.id id,
			r.name name,
			r.description description
		from
			user_role ur
		left join role r on
			ur.role_id = r.id
		where
			ur.user_id = #{userId}
	</select>
```
7、src/main/resources/mapper/PermissionMapper.xml

```sql
    <resultMap id="permissionResult" type="com.example.demo.entity.Permission">
        <result property="id" column="id" />
		<result property="name" column="name" />
        <result property="description" column="description" />
    </resultMap>

    <select id="findByPermissionListByRoleId" resultMap="permissionResult" parameterType="int">
        select
            p.id id,
            p.name name,
            p.description description
        from
            role_permission rp
        left join permission p on
            rp.permission_id = p.id
        where
            rp.role_id = #{roleId}
	</select>
```
8、页面
![login_fail](https://img-blog.csdnimg.cn/44f2f07558db4b95b8a496f5061811de.png#pic_center)

9、src/main/resources/static/index.html

```js
queryPermissionList() {
                axios.defaults.headers.token = this.token;
                axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
                axios({
                    method: 'post',
                    url: '/user/findPermissionListByRoleId',
                    data: JSON.stringify({
                        id: 0
                    })
                }).then(response => {
                    if(typeof response.data.error !== 'undefined' || response.data.error != null){
                        ELEMENT.Notification.error({
                            title: '查询失败！',
                            message: response.data.error,
                            position: 'top-right',
                            showClose: false,
                            offset: 110
                        });
                        return;
                    }
                    this.permissionData = response.data.allPermissionList;
                }).catch(error => {
                    console.log(error);
                    ELEMENT.Message(error);
                });
            },
```
10、com/example/demo/config/UserSessionManager.java

```java
package com.example.demo.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class UserSessionManager extends DefaultWebSessionManager {
    public static final String AUTHORIZATION="token";

    public UserSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取sessionId
        String sessionId= WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (sessionId!=null && sessionId!=""){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }else {
            return super.getSessionId(request,response);
        }
    }

}
```

11、参考链接：
[https://blog.csdn.net/qq_25046827/article/details/124540457](https://blog.csdn.net/qq_25046827/article/details/124540457)
[https://blog.csdn.net/palerock/article/details/73457415](https://blog.csdn.net/palerock/article/details/73457415)
### 前端控制按钮显隐，后端请求路径拦截配置
用户登陆成功后会返回用户具有的权限列表，根据权限列表控制前端控制按钮显隐及后端请求拦截路径，
总结如下：
![permission](https://img-blog.csdnimg.cn/0d3408bfad5a4db4bacbb45eefcd81aa.png#pic_center)

相关代码
com/example/demo/config/ShiroConfig.java

```java
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
        filterChainDefinitionMap.put("/", "anon");
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
        filterChainDefinitionMap.put("/**", "authc");
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

```
### 待完成
1、密码加解密；
2、记住我；
