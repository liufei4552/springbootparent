package com.java.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.java.shop.UserService;
import org.springframework.stereotype.Component;

/**
 * @ClassNameUserServiceImpl
 * @Description
 * @Author liufei
 * @Date2021/4/19 11:19
 * @Version V1.0
 **/
@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String name) {
        return "Hello:"+name;
    }
}
