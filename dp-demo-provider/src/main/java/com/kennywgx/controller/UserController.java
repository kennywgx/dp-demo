package com.kennywgx.controller;

import com.kennywgx.domain.UserDO;
import com.kennywgx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@Payload
public class UserController {
    @Autowired
    private UserMapper mapper;

//    @PostMapping("login")
//    public String login(String username, String password) {
//        mapper.selectList()
//    }

    @GetMapping("/")
    public List<UserDO> listAll() {
        return mapper.selectAll();
    }

}
