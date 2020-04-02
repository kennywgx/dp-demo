package com.kennywgx.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginOrRegisterForm {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
