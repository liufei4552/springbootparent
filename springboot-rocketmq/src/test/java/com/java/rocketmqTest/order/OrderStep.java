package com.java.rocketmqTest.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameOrderStep
 * @Description
 * @Author liufei
 * @Date2021/4/2 16:52
 * @Version V1.0
 **/
public class OrderStep {
    private long orderId;
    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
    public static List<OrderStep> buildOrders(){
        //1039L 创建、付款、推送、完成
        //1065L 创建、付款
        //7235L 创建、付款
        List<OrderStep>orderStepList=new ArrayList<>();

        OrderStep orderStep=new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("推送");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep=new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        return orderStepList;
    }
}
