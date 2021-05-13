package com.java.springboot.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.java.shop.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassNameUserController
 * @Description
 * @Author liufei
 * @Date2021/4/26 9:42
 * @Version V1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/sayHello")
    public String sayHello(String name){
        return userService.sayHello(name);
    }
}
