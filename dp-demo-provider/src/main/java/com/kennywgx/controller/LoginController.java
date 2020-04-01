package com.kennywgx.controller;

import com.kennywgx.domain.UserDO;
import com.kennywgx.exception.DemoException;
import com.kennywgx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Payload
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("login")
    public String login(@NotBlank(message = "用户名不能为空") String username,
                        @NotBlank(message = "密码不能为空") String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        // TODO catch AuthenticationException然后处理, 同时payload输出结果success指定为false
        return userService.login(username, password);
    }

    /**
     * 开发者账号注册
     */
    @PostMapping("developer/register")
    public String developerRegister(@NotBlank(message = "用户名不能为空") String username,
                           @NotBlank(message = "密码不能为空") String password) {
        UserDO developer = userService.register(username, password, "DEVELOPER");
        System.out.println(developer);
        return "注册成功";
    }

    /**
     * 职员账号注册
     */
    @PostMapping("staff/register")
    public String staffRegister(@NotBlank(message = "用户名不能为空") String username,
                           @NotBlank(message = "密码不能为空") String password) {
        UserDO register = userService.register(username, password, "STAFF");
        System.out.println(register);
        return "注册成功";
    }

}
