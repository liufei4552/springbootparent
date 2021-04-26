package com.java.rocketmqTest.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassNameConsumer
 * @Description
 * @Author liufei
 * @Date2021/4/9 9:20
 * @Version V1.0
 **/
@Slf4j
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //        1.创建消费者Consumer,制定消费者组名
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("group1");
//        2.指定ameserver地址
        consumer.setNamesrvAddr("192.168.71.128:9876;192.168.71.129:9876");
//        3.订阅主题Top1c和Tag
        consumer.subscribe("OrderTopic","*");

        //4、注册消息监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            /**
            * 功能描述:
             * @param msgs
             * @param context
            * @return: org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus
            * @Author: liufei
            * @Date: 2021/4/9 9:23
            */
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    log.info("消费消息:{},线程名称:{}",new String(msg.getBody()),Thread.currentThread().getName());
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        log.info("消费者启动.............................");
    }
}
