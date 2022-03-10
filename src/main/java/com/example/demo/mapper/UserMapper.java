package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户业务层
 */
@Mapper
public interface UserMapper {

    List<User> findUserByName(String userName);

    List<User> ListUser();

    List<User> queryPage(Integer startRows);

    int getRowCount();

    int insertUser(User user);

    int delete(int userId);

    int Update(User user);
}
