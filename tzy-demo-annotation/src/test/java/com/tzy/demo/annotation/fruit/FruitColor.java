package com.tzy.demo.annotation.fruit;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 水果颜色注解
 * @date 2020/5/15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     */
    public enum Color{ BULE,RED,GREEN};

    /**
     * 颜色属性
     * @return
     */
    Color fruitColor() default Color.GREEN;
}
