package com.java.rocketmq.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderStep
 * @Description
 * @Author liufei
 * @Date 2020/7/14 17:10
 * @Version V1.0
 **/
@Data
public class OrderStep {
    private Long orderId;
    private String desc;

    public OrderStep(Long orderId, String desc) {
        this.orderId = orderId;
        this.desc = desc;
    }

    public OrderStep() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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
        //1029L  创建、付款、推送、完成
        //1065L  创建  付款
        //7235L  创建  付款
        List<OrderStep>orderStepList=new ArrayList<>();
        OrderStep orderStep=new OrderStep();
        orderStep.setOrderId(1029L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        OrderStep orderStep0=new OrderStep();
        orderStep0.setOrderId(1065L);
        orderStep0.setDesc("创建");
        orderStepList.add(orderStep0);

        OrderStep orderStep1=new OrderStep();
        orderStep1.setOrderId(1029L);
        orderStep1.setDesc("付款");
        orderStepList.add(orderStep1);

        OrderStep orderStep4=new OrderStep();
        orderStep4.setOrderId(7235L);
        orderStep4.setDesc("创建");
        orderStepList.add(orderStep4);

        OrderStep orderStep5=new OrderStep();
        orderStep5.setOrderId(1065L);
        orderStep5.setDesc("付款");
        orderStepList.add(orderStep5);

        OrderStep orderStep2=new OrderStep();
        orderStep2.setOrderId(1029L);
        orderStep2.setDesc("推送");
        orderStepList.add(orderStep2);

        OrderStep orderStep3=new OrderStep();
        orderStep3.setOrderId(1029L);
        orderStep3.setDesc("完成");
        orderStepList.add(orderStep3);

        OrderStep orderStep6=new OrderStep();
        orderStep6.setOrderId(7235L);
        orderStep6.setDesc("付款");
        orderStepList.add(orderStep6);

        return orderStepList;
    }
}
