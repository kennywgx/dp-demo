package com.kennywgx.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private Integer id;

    private String username;

    private String password;

    private List<String> roles;
}
