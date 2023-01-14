package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务层
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     *
     * @param userName userName
     * @return User
     */
    User findUserByName(@Param("userName") String userName);

    /**
     * 通过角色id删除用户角色关系表中数据
     *
     * @param id roleId
     * @return int
     */
    int removeUserRoleByRoleId(int id);

    /**
     * 通过用户id删除用户角色关系表中数据
     *
     * @param id userId
     * @return int
     */
    int removeUserRoleByUserId(int id);

    /**
     * 向用户角色关系表中批量插入数据
     *
     * @param user user
     * @return int
     */
    int addUserRole(User user);


    /**
     * 根据用户信息分页查询用户
     *
     * @param user user
     * @return List<User>
     */
    List<User> queryUserListPage(User user);

    /**
     * 统计用户个数
     *
     * @param user user
     * @return int
     */
    int getUserRowCount(User user);


    int insertUser(User user);


}
