package com.tzy.demo.annotation.model;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 颜色注解
 * @date 2020/5/15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyColor {
    /**
     * 颜色枚举
     */
    public enum Color{BULE,RED,GREEN};

    /**
     * 颜色属性
     * @return
     */
    Color myColor() default Color.GREEN;
}
