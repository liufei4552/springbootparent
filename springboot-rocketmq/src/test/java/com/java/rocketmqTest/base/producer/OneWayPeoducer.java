package com.java.rocketmqTest.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassNameOneWayPeoducer
 * @Description发送单向消息，常用于日志记录
 * @Author liufei
 * @Date2021/3/19 15:42
 * @Version V1.0
 **/
@Slf4j
public class OneWayPeoducer {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        //    1.创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer=new DefaultMQProducer("group1");
        //    2.指定Nameservec地址
        producer.setNamesrvAddr("192.168.71.128:9876;192.168.71.129:9876");
        //    3.启动producer
        producer.start();
        for (int i = 0; i <10 ; i++) {
            //    4.创建消息对象，指定主题Topic. Tag和消息体
            /**
             * 参数一：消息主题topic
             * 参数二：消息tag
             * 参数三：消息内容
             */
            Message msg=new Message("base","tag3",("Hello World 单向消息"+i).getBytes());
            //    5.发送消息
            producer.sendOneway(msg);

            TimeUnit.SECONDS.sleep(1);
        }
        //    6.关闭生产者producer
        producer.shutdown();
    }
}
