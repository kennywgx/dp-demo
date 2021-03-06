package com.kennywgx.controller;

import lombok.Data;
import com.kennywgx.service.DemoService;
import com.kennywgx.exception.DemoException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.deepexi.pojo.converter.utils.ConverterUtils.convert;

@RestController
@RequestMapping("demo")
@Payload
public class DemoController {

    @GetMapping("greeting")
    public String sayHello() {
        return "welcome!中文";
    }

    @GetMapping("convert")
    public Model doConvert() {
        return convert("welcome!", Model.class);
    }

    @GetMapping("sys-error")
    public void syserror() {
        throw new RuntimeException();
    }

    @GetMapping("biz-error")
    public void bizerror() {
        throw new DemoException("biz-error");
    }

    @Data
    public static class Model {
        private String content;
    }
}
