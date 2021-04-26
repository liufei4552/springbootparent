package com.java.rocketmq.base.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName OneWayProducer
 * @Description 发送单向消息
 * @Author liufei
 * @Date 2020/7/14 16:05
 * @Version V1.0
 **/
@Slf4j
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1、创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer=new DefaultMQProducer("group1");
        //2、制定NameServer地址
        producer.setNamesrvAddr("192.168.242.128:9876;192.168.242.129:9876");
        //3、启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4、创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数1 消息主题Topic
             * 参数1 消息Tag
             * 参数1 消息内容
             */
            Message msg=new Message("base","Tag3",("Hello World 单向消息"+i).getBytes());
            //5、发送单向消息
            producer.sendOneway(msg);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6、关闭生产者producer
        producer.shutdown();
    }
}
