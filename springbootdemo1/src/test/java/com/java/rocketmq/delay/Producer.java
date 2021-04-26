package com.java.rocketmq.delay;

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
 * @ClassName Producer
 * @Description 发送延迟消息
 * @Author liufei
 * @Date 2020/7/15 10:40
 * @Version V1.0
 **/
@Slf4j
public class Producer {
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
            Message msg=new Message("delayTopic","Tag1",("Hello World"+i).getBytes());
            //设置延迟时间 messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(2);
            //5、发送消息
            SendResult result=producer.send(msg);
            SendStatus status=result.getSendStatus();
            String msgId=result.getMsgId();
            int queueId=result.getMessageQueue().getQueueId();
            log.info("发送状态:status={},消息ID:id={},队列ID:id={}",status,msgId,queueId);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6、关闭生产者producer
        producer.shutdown();
    }
}
