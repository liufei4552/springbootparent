package com.java.rocketmqTest.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassNameSyncProducer
 * @Description 发送事务消息
 * @Author liufei
 * @Date2021/3/19 14:41
 * @Version V1.0
 **/
@Slf4j
public class Producer {
        public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
                //    1.创建消息生产者producer,并制定生产者组名
                TransactionMQProducer producer=new TransactionMQProducer("group5");
                //    2.指定Nameservec地址
                producer.setNamesrvAddr("192.168.71.128:9876;192.168.71.129:9876");

                producer.setTransactionListener(new TransactionListener() {
                        /**
                         * 在该方法中执行本地事务
                         * @param msg
                         * @param arg
                         * @return
                         */
                        @Override
                        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                                if(StringUtils.equals("TAGA",msg.getTags())){
                                        return LocalTransactionState.COMMIT_MESSAGE;
                                }else if(StringUtils.equals("TAGB",msg.getTags())){
                                        return LocalTransactionState.ROLLBACK_MESSAGE;
                                }else{
                                        return LocalTransactionState.UNKNOW;
                                }
                        }

                        /**
                         * 该方法是mq消息事务状态的回查
                         * @param msg
                         * @return
                         */
                        @Override
                        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                                log.debug("消息的tag={}",msg.getTags());
                                return LocalTransactionState.COMMIT_MESSAGE;
                        }
                });
                //    3.启动producer
                producer.start();
                String[] tags={"TAGA","TAGB","TAGC"};
                for (int i = 0; i <3 ; i++) {
                        //    4.创建消息对象，指定主题Topic. Tag和消息体
                        /**
                         * 参数一：消息主题topic
                         * 参数二：消息tag
                         * 参数三：消息内容
                         */
                        Message msg=new Message("TransactionTopic",tags[i],("Hello World"+i).getBytes());
                        //    5.发送消息
                        SendResult result = producer.sendMessageInTransaction(msg,null);
                        SendStatus sendStatus = result.getSendStatus();
                        String msgId = result.getMsgId();
                        int queueId = result.getMessageQueue().getQueueId();
                        log.debug("返回值:{}",result);
                        log.debug("发送状态:{},消息ID:{},队列:{}",sendStatus,msgId,queueId);
                        TimeUnit.SECONDS.sleep(1);
                }
                //    6.关闭生产者producer
                //producer.shutdown();
        }
}
