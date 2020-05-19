package com.tzy.demo.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangzanyong
 * @description @测试
 * @date 2020/5/12
 **/
@RestController
@Api(tags = "测试接口",description = "测试接口 Rest Api")
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    @ApiOperation(value = "测试", httpMethod = "GET", notes = "测试接口")
    public String test(String userName){
        System.out.println(userName);
        return userName;
    }
}
