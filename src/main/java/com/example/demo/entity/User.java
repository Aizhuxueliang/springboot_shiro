package com.example.demo.entity;

import lombok.*;

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

}
