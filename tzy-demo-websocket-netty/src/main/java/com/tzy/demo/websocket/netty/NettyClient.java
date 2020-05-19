package com.tzy.demo.websocket.netty;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/16
 **/
@Component
public class NettyClient {

    public void sendMessage(String message) {
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        NettyServer.channelGroup.writeAndFlush(tws);
    }
}
