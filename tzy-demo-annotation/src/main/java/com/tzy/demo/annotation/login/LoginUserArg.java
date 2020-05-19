package com.tzy.demo.annotation.login;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 声明 登录用户参数 注解
 * @date 2020/5/15
 **/
@Documented
@Target({ ElementType.PARAMETER }) //用于描述方法参数变量
@Retention(RetentionPolicy.RUNTIME) //在运行时有效 保留到运行时，可通过注解获取
public @interface LoginUserArg {
}
