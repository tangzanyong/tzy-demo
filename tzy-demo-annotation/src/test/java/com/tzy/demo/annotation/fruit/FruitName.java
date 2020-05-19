package com.tzy.demo.annotation.fruit;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 水果名称注解
 * @date 2020/5/15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "aa";
}
