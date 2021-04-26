package com.java.springboot.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameProducerAppliction
 * @Description 生产者启动类
 * @Author liufei
 * @Date2021/4/15 14:38
 * @Version V1.0
 **/
@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }
}
