package com.tzy.demo.annotation.model;

import lombok.Data;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/15
 **/
@Data
public class Dog {
    @MyName("aa")
    private String name;

    @MyColor(myColor = MyColor.Color.RED)
    private String color;
}
