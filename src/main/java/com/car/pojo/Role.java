package com.car.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private Integer id;

    private String rolename;

    private Integer roleState;

    private List<Menu> menuList;
}
