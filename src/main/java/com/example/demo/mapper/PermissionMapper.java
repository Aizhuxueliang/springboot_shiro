package com.example.demo.mapper;

import com.example.demo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限
 */
@Mapper
public interface PermissionMapper {

    /**
     * 根据roleId查询所有权限
     * @param roleId
     * @return
     */
    List<Permission> findByPermissionListByRoleId(@Param("roleId") int roleId);
}
