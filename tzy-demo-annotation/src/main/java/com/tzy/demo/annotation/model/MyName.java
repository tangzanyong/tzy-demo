package com.tzy.demo.annotation.model;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 名称注解
 * @date 2020/5/15
 **/
@Target(ElementType.FIELD) //注解用于字段上
@Retention(RetentionPolicy.RUNTIME)  //保留到运行时，可通过注解获取
@Documented
public @interface MyName {
    String value() default "tzy";
}
