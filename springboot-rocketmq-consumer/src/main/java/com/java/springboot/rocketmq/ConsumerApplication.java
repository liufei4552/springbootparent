package com.java.springboot.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameProducerAppliction
 * @Description 消费者启动类
 * @Author liufei
 * @Date2021/4/15 14:38
 * @Version V1.0
 **/
@SpringBootApplication
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
        System.out.println("消费者启动成功........................");
    }
}
