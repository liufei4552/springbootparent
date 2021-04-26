package com.java.rocketmq.batch;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Producer
 * @Description 发送批量消息(单个消息不能超过4M，超过就要分割)
 * @Author liufei
 * @Date 2020/7/15 11:04
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


            //4、创建消息对象，指定主题Topic、Tag和消息体
        List<Message>msgs=new ArrayList<>();
            /**
             * 参数1 消息主题Topic
             * 参数1 消息Tag
             * 参数1 消息内容
             */
            Message msg1=new Message("batchTopic","Tag1",("Hello World"+1).getBytes());
            Message msg2=new Message("batchTopic","Tag1",("Hello World"+2).getBytes());
            Message msg3=new Message("batchTopic","Tag1",("Hello World"+3).getBytes());
            Message msg4=new Message("batchTopic","Tag1",("Hello World"+4).getBytes());
            Message msg5=new Message("batchTopic","Tag1",("Hello World"+5).getBytes());
            msgs.add(msg1);
            msgs.add(msg2);
            msgs.add(msg3);
            msgs.add(msg4);
            msgs.add(msg5);


            //5、发送消息
            SendResult result=producer.send(msgs);
            SendStatus status=result.getSendStatus();
            String msgId=result.getMsgId();
            int queueId=result.getMessageQueue().getQueueId();
            log.info("发送状态:status={},消息ID:id={},队列ID:id={}",status,msgId,queueId);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);

        //6、关闭生产者producer
        producer.shutdown();
    }
}
