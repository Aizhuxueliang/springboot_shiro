package com.example.demo.mapper;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 角色
 */
@Mapper
public interface RoleMapper {
    /**
     * 根据用户查询所有的角色
     *
     * @param userId 用户id
     * @return
     */
    List<Role> findRoleListByUserId(@Param("userId") int userId);
}
