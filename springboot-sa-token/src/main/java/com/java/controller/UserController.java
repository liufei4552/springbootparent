package com.java.controller;

import com.java.common.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassNameHelloController
 * @Description
 * @Author liufei
 * @Date2021/5/13 16:01
 * @Version V1.0
 **/

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String first(@Param("username")String username,@Param("password")String password){
        System.out.println("12356");
        log.error("username={},password={}",username,password);
        return "你好";
    }
}
