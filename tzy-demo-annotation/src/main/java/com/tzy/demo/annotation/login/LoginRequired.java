package com.tzy.demo.annotation.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tangzanyong
 * @description 必须登录注解
 * @date 2020/5/15
 **/
@Target(ElementType.METHOD) //用于描述方法
@Retention(RetentionPolicy.RUNTIME) //保留到运行时，可通过注解获取
public @interface LoginRequired {
}
