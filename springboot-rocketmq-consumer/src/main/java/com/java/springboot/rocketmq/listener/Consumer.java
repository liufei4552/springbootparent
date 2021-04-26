package com.java.springboot.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassNameConsumer
 * @Description
 * @Author liufei
 * @Date2021/4/15 15:06
 * @Version V1.0
 **/
@RocketMQMessageListener(topic = "springboot-rocketmq",consumerGroup = "${rocketmq.consumer.group}")
@Slf4j
@Component
public class Consumer implements RocketMQListener<User> {
    @Override
    public void onMessage(User message) {
        System.out.println("接收到消息:"+message.toString());
    }
}
