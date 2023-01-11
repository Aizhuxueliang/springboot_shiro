package com.example.demo.mapper;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.*;

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
     * @return List<Role>
     */
    List<Role> findRoleListByUserId(@Param("userId") int userId);

    /**
     * 通过角色id删除角色权限关系表中数据
     *
     * @param id 角色id
     * @return int
     */
    int removeRolePermissionByRoleId(int id);

    /**
     * 向角色权限关系表中批量插入数据
     *
     * @param role role
     * @return int
     */
    int addRolePermission(Role role);

    /**
     * 通过角色id删除角色中角色
     *
     * @param id id
     * @return int
     */
    int removeRoleByRoleId(int id);

    /**
     * 向角色表中插入单条数据
     *
     * @param role role
     * @return int
     */
    int addRole(Role role);
}
