package com.java.rocketmq.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @ClassName Consumer
 * @Description 接收批量消息
 * @Author liufei
 * @Date 2020/7/15 11:05
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
        consumer.subscribe("batchTopic","*");
        //设置消费模式(（CLUSTERING）负载均衡/（broadcast）广播)
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        //4、设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * 接收消息内容
             * @param msgs
             * @param context
             * @return
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (int i = 0; i <msgs.size(); i++) {
                    log.info("内容:{}",new String(msgs.get(i).getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5、启动消费者consumer
        consumer.start();
    }
}
