package com.car.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private Integer roleId;

    private String nickname;

    private String password;

    private Account account;

    private Role role;
}
