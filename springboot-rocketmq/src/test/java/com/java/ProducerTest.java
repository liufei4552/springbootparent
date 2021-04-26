package com.java;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * @ClassName ProducerTest
 * @Description
 * @Author liufei
 * @Date 2020/7/15 15:21
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes={SpringbootRocketmqApplication.class})
public class ProducerTest {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Test
    public void testSendMessage(){
        rocketMQTemplate.convertAndSend("springboot-rocketmq","hello springboot-rocket");
    }
}
