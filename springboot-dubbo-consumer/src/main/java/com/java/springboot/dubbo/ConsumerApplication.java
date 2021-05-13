package com.java.springboot.dubbo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameConsumerApplication
 * @Description
 * @Author liufei
 * @Date2021/4/26 9:40
 * @Version V1.0
 **/
@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
