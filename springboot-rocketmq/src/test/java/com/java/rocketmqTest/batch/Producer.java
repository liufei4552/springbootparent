package com.java.rocketmqTest.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.hibernate.validator.constraints.EAN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassNameProducer
 * @Description 发送批量消息(最大大小不能超过4M)
 * @Author liufei
 * @Date2021/4/9 12:35
 * @Version V1.0
 **/
@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        //    1.创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer=new DefaultMQProducer("producer");
        //    2.指定Nameservec地址
        producer.setNamesrvAddr("192.168.71.128:9876;192.168.71.129:9876");
        //    3.启动producer
        producer.start();

        List<Message>messages=new ArrayList<>();

            //    4.创建消息对象，指定主题Topic. Tag和消息体
            /**
             * 参数一：消息主题topic
             * 参数二：消息tag
             * 参数三：消息内容
             */
            Message msg1=new Message("BatchTopic","tag1",("Hello World"+1).getBytes());
        Message msg2=new Message("BatchTopic","tag1",("Hello World"+2).getBytes());
        Message msg3=new Message("BatchTopic","tag1",("Hello World"+3).getBytes());
        Message msg4=new Message("BatchTopic","tag1",("Hello World"+4).getBytes());
        messages.add(msg1);
        messages.add(msg2);
        messages.add(msg3);
        messages.add(msg4);
            //    5.发送消息
            SendResult result = producer.send(messages);
            SendStatus sendStatus = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            log.debug("返回值:{}",result);
            log.debug("发送状态:{},消息ID:{},队列:{}",sendStatus,msgId,queueId);
            TimeUnit.SECONDS.sleep(1);
        //    6.关闭生产者producer
        producer.shutdown();
    }
}
