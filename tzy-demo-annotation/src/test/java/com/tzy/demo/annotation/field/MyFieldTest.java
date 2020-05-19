package com.tzy.demo.annotation.field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

/**
 * @author tangzanyong
 * @description 通过反射获取注解
 * @date 2020/5/15
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyFieldTest {
    //使用我们的自定义注解
    @MyField(description = "用户名", length = 12)
    private String username;

    //通过反射获取注解
    @Test
    public void testMyField(){
        // 获取类模板
        Class c = MyFieldTest.class;
        // 获取所有字段
        for(Field f : c.getDeclaredFields()){
            // 判断这个字段是否有MyField注解
            if(f.isAnnotationPresent(MyField.class)){
                MyField annotation = f.getAnnotation(MyField.class);
                System.out.println("字段:[" + f.getName() + "], 描述:[" + annotation.description() + "], 长度:[" + annotation.length() +"]");
            }
        }
    }
}
