package com.tzy.demo.websocket.netty;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangzanyong
 * @description WebSocket-Netty服务器向客户端发送消息
 * @date 2020/5/16
 **/
@RestController
@RequestMapping("/netty")
public class NettyController {
    @Autowired
    private NettyServer nettyServer;

    @RequestMapping("/webSocket")
    public String index(@RequestParam("message") String message){
        nettyServer.sendMessage(message);
        return "发送消息：" + message;
    }
}
