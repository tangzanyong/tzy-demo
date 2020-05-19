package com.tzy.demo.mqtt.api;

import com.tzy.demo.mqtt.config.MqttGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangzanyong
 * @description 测试推送mqtt消息
 * @date 2020/5/18
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private MqttGateway mqttGateway;

    @GetMapping("/sendMsg")
    public String sendMsg(){
        //发送hello主题的消息
        mqttGateway.sendToMqtt("hello",1, "hello mqtt");
        return "send msg success";
    }
}
