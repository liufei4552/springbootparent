package com.java.rocketmqTest.filter.tag;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassNameConsumer
 * @Description 消费消息
 * @Author liufei
 * @Date2021/3/19 16:29
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
        consumer.subscribe("filterTagTopic","tag1");
        //设置消费模式(默认负载均衡) BROADCASTING广播  CLUSTERING负载均衡
        //consumer.setMessageModel(MessageModel.BROADCASTING);
//        4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * 接收消息内容
             * @param msgs
             * @param context
             * @return
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    log.debug("收到消息为={}",new String(msg.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
//        5.启动消费者consumer
        consumer.start();
        log.debug("消费者已启动.................");
    }
}
