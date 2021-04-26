package com.java.rocketmq.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName Consumer
 * @Description 接收顺序消息
 * @Author liufei
 * @Date 2020/7/14 17:57
 * @Version V1.0
 **/
@Slf4j
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //1、创建消费者consumer，制定消费者组名
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("group1");
        //2、指定nameserver地址
        consumer.setNamesrvAddr("192.168.242.128:9876;192.168.242.129:9876");
        //3、订阅主题topic和tag
        consumer.subscribe("OrderTopic","*");

        //4、注册消息监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    log.info("线程名称:"+Thread.currentThread().getName()+"消费消息:{}",new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        log.info("消费者启动。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
    }
}
