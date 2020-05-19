package com.tzy.demo.annotation;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/15
 **/
@SpringBootApplication
@EnableSwagger2Doc
public class AnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }

}
