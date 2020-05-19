package com.tzy.demo.mqtt.config;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 定义重载方法，用于消息发送
     */
    void sendToMqtt(String data);

    /**
     * 指定topic发送
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);


    /**
     * 指定topic与等级进行消息发送
     *
     *  【QOS】:
     *      0代表“至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
     *      1代表“至少一次”，确保消息到达，但消息重复可能会发生。
     *      2代表“只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果。
     *
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String data);
}
