package com.java.globalexception;

/**
 * @ClassNameMyExceptionHandler
 * @Description
 * @Author liufei
 * @Date2021/7/12 9:33
 * @Version V1.0
 **/
public interface BaseErrorInfoInterface {

    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
