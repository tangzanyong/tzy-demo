package com.tzy.demo.mqtt;

import com.tzy.demo.mqtt.config.MqttGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/18
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MqttTest {
    @Resource
    private MqttGateway mqttGateway;

    @Test
    public void testMqtt(){
        mqttGateway.sendToMqtt("tzy",1, "hello mqtt");
    }

}
