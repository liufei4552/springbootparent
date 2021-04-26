package com.java.rocketmqTest.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @ClassNameProducer
 * @Description 顺序消息生产者
 * @Author liufei
 * @Date2021/4/8 13:35
 * @Version V1.0
 **/
@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //    1.创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer=new DefaultMQProducer("group1");
        //    2.指定Nameservec地址
        producer.setNamesrvAddr("192.168.71.128:9876;192.168.71.129:9876");
        //    3.启动producer
        producer.start();
        //构建消息集合
        List<OrderStep>orderSteps=OrderStep.buildOrders();
        //发送消息
        for (int i=0;i<orderSteps.size();i++) {
            String body=orderSteps.get(i)+"";
            Message message=new Message("OrderTopic","Order","i"+i,body.getBytes());
            /**
             * 参数一:消息对象
             * 参数二：消息队列的选择器
             * 参数三:选择队列的业务标识（订单号）
             */
            SendResult sendResult=producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param mqs 队列集合
                 * @param msg 消息对象
                 * @param arg 业务标识参数(就是上面的参数三)
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Long orderId= (Long) arg;
                    Long index=orderId % mqs.size();
                    return mqs.get(Math.toIntExact(index));
                }
            },orderSteps.get(i).getOrderId());
            log.info("发送结果:{}",sendResult);
        }
        producer.shutdown();
    }
}
