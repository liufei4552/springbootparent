package com.java.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassNameParam
 * @Description
 * @Author liufei
 * @Date2021/4/20 11:42
 * @Version V1.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    /**
     * 值
     */
    String value() default "";

    /**
     * 是否必须
     */
    boolean require() default false;
}
