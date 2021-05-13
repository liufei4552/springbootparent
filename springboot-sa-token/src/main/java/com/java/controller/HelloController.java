package com.java.controller;

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
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/first",method = RequestMethod.GET)
    public String first(){
        return "你好";
    }
}
