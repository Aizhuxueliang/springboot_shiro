package com.example.demo.entity;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
