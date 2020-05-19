package com.tzy.demo.annotation.logger;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 系统日志注解
 * @date 2020/5/16
 **/
@Target(ElementType.METHOD) //用于描述方法
@Retention(RetentionPolicy.RUNTIME) //保留到运行时，可通过注解获取
public @interface SysLog {

}
