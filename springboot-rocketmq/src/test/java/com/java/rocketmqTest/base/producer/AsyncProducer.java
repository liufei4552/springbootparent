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
 * @ClassNameAsyncProducer
 * @Description发送异步消息
 * @Author liufei
 * @Date2021/3/19 15:28
 * @Version V1.0
 **/
@Slf4j
public class AsyncProducer {
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
            Message msg=new Message("base","tag2",("Hello World"+i).getBytes());
            //    5.发送异步消息
             producer.send(msg, new SendCallback() {
                 /**
                  * 发送成功回调函数
                  * @param sendResult
                  */
                 @Override
                 public void onSuccess(SendResult sendResult) {
                     log.debug("发送结果:{}",sendResult);
                 }

                 /**
                  * 发送失败回调结果
                  * @param e
                  */
                 @Override
                 public void onException(Throwable e) {
                    log.debug("发送异常:{}",e);
                 }
             });

            TimeUnit.SECONDS.sleep(1);
        }
        //    6.关闭生产者producer
        producer.shutdown();
    }
}
