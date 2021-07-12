package com.java.rocketmq.order;

import com.alibaba.fastjson.JSONObject;
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
 * @ClassName Producer
 * @Description 顺序消息
 * @Author liufei
 * @Date 2020/7/14 17:24
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
        //构建消息集合
        List<OrderStep>orderStepList=OrderStep.buildOrders();
        for (int i = 0; i <orderStepList.size(); i++) {
            /**
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识(订单id)
             */
            Message msg=new Message("OrderTopic","Order","i"+i, JSONObject.toJSONString(orderStepList.get(i)).getBytes());
            SendResult sendResult=producer.send(msg, new MessageQueueSelector() {
                /**
                 *
                 * @param mqs 队列集合
                 * @param msg 消息对象
                 * @param arg 业务标识的参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long orderId=(long) arg;
                    long index=orderId % mqs.size();
                    log.info("消息体:{},队列下标:{}",new String(msg.getBody()),mqs.get((int)index));
                    return mqs.get((int)index);
                }
            },orderStepList.get(i).getOrderId());
            log.info("发送结果：{}",sendResult);
        }
        producer.shutdown();
    }
}
