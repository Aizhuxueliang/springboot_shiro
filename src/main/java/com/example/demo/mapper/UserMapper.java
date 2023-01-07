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

    List<User> findByName(String userName);

    User findUserByName(@Param("userName") String userName);

    List<User> listUser();

    List<User> queryPage(Integer startRows);

    int getRowCount();

    int insertUser(User user);

    int delete(int userId);

    int modify(User user);

}
