package com.kennywgx.controller;

import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.JWTManager;
import com.kennywgx.domain.UserDO;
import com.kennywgx.mapper.UserMapper;
import com.kennywgx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user")
@Payload
public class UserController {

    public final static String issuer = "one";

    @Autowired
    private UserService userService;

    @Autowired
    private JWTManager jwtManager;

    @GetMapping("/")
    public List<UserDO> listAll() {
        return userService.listAll();
    }

}
