package com.java.rocketmq.transaction;


import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Producer
 * @Description 事务消息
 * @Author liufei
 * @Date 2020/7/15 11:59
 * @Version V1.0
 **/
@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1、创建消息生产者producer,并制定生产者组名
        TransactionMQProducer producer=new TransactionMQProducer("group5");
        //2、制定NameServer地址
        producer.setNamesrvAddr("192.168.242.128:9876;192.168.242.129:9876");

        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法中执行本地事务
             * @param msg
             * @param arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if("Taga".equals(msg.getTags())){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if("Tagb".equals(msg.getTags())){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            /**
             * 该方法是mq进行消息事务状态的回查
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                log.info("消息回查tag:{}",msg.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        //3、启动producer
        producer.start();
        String [] tags={"Taga","Tagb","Tagc"};
        for (int i = 0; i < 3; i++) {
            //4、创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数1 消息主题Topic
             * 参数1 消息Tag
             * 参数1 消息内容
             */
            Message msg=new Message("transactionTopic",tags[i],("Hello World"+i).getBytes());
            //5、发送消息
            SendResult result=producer.sendMessageInTransaction(msg,null);
            SendStatus status=result.getSendStatus();
            String msgId=result.getMsgId();
            int queueId=result.getMessageQueue().getQueueId();
            log.info("发送状态:status={},消息ID:id={},队列ID:id={}",status,msgId,queueId);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6、关闭生产者producer
        //producer.shutdown();
    }
}
