package com.kennywgx.controller;

import com.kennywgx.config.web.DemoResponse;
import com.kennywgx.controller.form.LoginOrRegisterForm;
import com.kennywgx.domain.UserDO;
import com.kennywgx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("login")
    public DemoResponse<String> login(@RequestBody @Valid LoginOrRegisterForm form) {
        System.out.println("username: " + form.getUsername());
        System.out.println("password: " + form.getPassword());

        String token = userService.login(form.getUsername(), form.getPassword());
        return DemoResponse.success(token);
    }

    /**
     * 开发者账号注册
     */
    @PostMapping("developer/register")
    public DemoResponse<String> developerRegister(@RequestBody @Valid LoginOrRegisterForm form) {
        UserDO developer = userService.register(form.getUsername(), form.getPassword(), "DEVELOPER");
        System.out.println(developer);
        return DemoResponse.success(null, "注册成功");
    }

    /**
     * 职员账号注册
     */
    @PostMapping("staff/register")
    public DemoResponse<String> staffRegister(@RequestBody @Valid LoginOrRegisterForm form) {
        UserDO developer = userService.register(form.getUsername(), form.getPassword(), "STAFF");
        System.out.println(developer);
        return DemoResponse.success(null, "注册成功");
    }

}
