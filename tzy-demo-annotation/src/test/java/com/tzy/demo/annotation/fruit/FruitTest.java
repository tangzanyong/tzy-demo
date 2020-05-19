package com.tzy.demo.annotation.fruit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/15
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class FruitTest {
    @Test
    public void testApple1(){
        Apple apple = new Apple();
        System.out.println(apple.getAppleName());
    }

    @Test
    public void testFruit(){
        Field[] fields = Apple.class.getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(FruitName.class)){
                FruitName fruitName = field.getAnnotation(FruitName.class);
                System.out.println("水果名称：" + fruitName.value());
            }else if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                System.out.println("水果颜色：" + fruitColor.fruitColor());
            }else if(field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                System.out.println("供应商编号：" + fruitProvider.id() + ",供应商名称："+fruitProvider.name() + ",供应商地址：" + fruitProvider.address());
            }
        }

    }

    @Test
    public void testFruitInfo(){
        FruitInfoUtil.getFruitInfo(Apple.class);
    }

}
