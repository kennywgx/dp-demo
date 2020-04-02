package com.kennywgx.controller;

import com.kennywgx.config.web.DemoResponse;
import com.kennywgx.domain.UserDO;
import com.kennywgx.exception.DemoException;
import com.kennywgx.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("login")
    public DemoResponse<String> login(@NotBlank(message = "用户名不能为空") String username,
                                      @NotBlank(message = "密码不能为空") String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        try {
            String token = userService.login(username, password);
            return DemoResponse.success(token);
        } catch (AuthenticationException e) {
            return DemoResponse.fail(e.getMessage());
        }
    }

    /**
     * 开发者账号注册
     */
    @PostMapping("developer/register")
    public DemoResponse<String> developerRegister(@NotBlank(message = "用户名不能为空") String username,
                                                  @NotBlank(message = "密码不能为空") String password) {
        try {
            UserDO developer = userService.register(username, password, "DEVELOPER");
            System.out.println(developer);
        } catch (DemoException e) {
            return DemoResponse.fail(e.getMessage());
        }
        return DemoResponse.success(null, "注册成功");
    }

    /**
     * 职员账号注册
     */
    @PostMapping("staff/register")
    public DemoResponse<String> staffRegister(@NotBlank(message = "用户名不能为空") String username,
                                              @NotBlank(message = "密码不能为空") String password) {
        try {
            UserDO developer = userService.register(username, password, "STAFF");
            System.out.println(developer);
        } catch (DemoException e) {
            return DemoResponse.fail(e.getMessage());
        }
        return DemoResponse.success(null, "注册成功");
    }

    @GetMapping("test")
    @ResponseBody
    public String test() {
        System.out.println("撒发生");
        return "中文";
    }

}
