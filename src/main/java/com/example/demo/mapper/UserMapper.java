package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户业务层
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    User findUserByName(@Param("userName") String userName);

    /**
     * 根据用户名模糊查询用户
     *
     * @param userName
     * @return
     */
    List<User> findUserByLikeName(@Param("userName") String userName);

    /**
     * 通过角色id删除用户角色关系表中数据
     *
     * @param id
     * @return
     */
    int removeUserRoleByRoleId(int id);

    List<User> queryPage(Integer startRows);

    int getRowCount();

    int insertUser(User user);

    int delete(int userId);

    int modify(User user);

}
