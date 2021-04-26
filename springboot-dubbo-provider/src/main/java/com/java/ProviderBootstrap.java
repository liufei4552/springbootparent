package com.java;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameProviderBootstrap
 * @Description
 * @Author liufei
 * @Date2021/4/19 10:11
 * @Version V1.0
 **/
@SpringBootApplication
@EnableDubboConfiguration
public class ProviderBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ProviderBootstrap.class,args);
    }
}
