package com.java;

import com.java.model.po.User;
import com.java.springboot.rocketmq.ProducerApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassNameProducerTest
 * @Description
 * @Author liufei
 * @Date2021/4/15 14:41
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProducerApplication.class})
@Slf4j
public class ProducerTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Test
    public void testSend(){
        User user=new User();
        user.setUserId("123456789");
        user.setUserName("liufei");
        rocketMQTemplate.convertAndSend("springboot-rocketmq",user);
        log.debug("消息发送成功.............");
    }
}
