package com.tzy.demo.annotation.fruit;

import java.lang.annotation.*;

/**
 * @author tangzanyong
 * @description 水果供应者注解
 * @date 2020/5/15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
    /**
     * 供应商编号
     * @return
     */
    public int id() default -1;

    /**
     * 供应商名称
     * @return
     */
    public String name() default "";

    /**
     * 供应商地址
     * @return
     */
    public String address() default "";

}
