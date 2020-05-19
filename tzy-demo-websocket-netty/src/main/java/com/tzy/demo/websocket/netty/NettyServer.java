package com.tzy.demo.websocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/16
 **/
@Slf4j
@Component
public class NettyServer {
    /**
     * 存储每一个客户端接入进来时的channel对象
     */
    public final static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 开启，默认关闭
     */
    @Value("${netty.websocket.enabled}")
    private boolean enabled;
    /**
     * websocket监听端口
     */
    @Value("${netty.websocket.port}")
    private int port;

    /**
     * 当前连接的通道
     */
    private Channel channel;

    /**
     * 开启netty服务
     */
    public void run() {
        if (enabled) {
            log.info("===========================WebSocket-Netty服务正在启动=====================");
            // Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket， （有点像门卫）然后把这些socket传给worker线程池。
            // 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            // Worker线程：Worker线程执行所有的异步I/O，即处理操作
            EventLoopGroup workGroup = new NioEventLoopGroup();
            try {
                // ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workGroup);
                // 设置非阻塞,用它来建立新accept的连接,用于构造serversocketchannel的工厂类
                b.channel(NioServerSocketChannel.class);
                // ChildChannelHandler 对出入的数据进行的业务操作,其继承ChannelInitializer
                b.childHandler(new WebSocketChannelHandler());
                log.info("服务端启动成功，等待客户端连接 ... ...");
                channel = b.bind(port).sync().channel();
                channel.closeFuture().sync();
            } catch (Exception e) {
                log.error("服务端启动失败", e);
                e.printStackTrace();
            } finally {
                //释放线程池资源
                if(channel!=null){
                    channel.close();
                }
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
                log.info("服务端已关闭");
            }
        }
    }

    //发送消息
    public void sendMessage(String message) {
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        channelGroup.writeAndFlush(tws);
    }
}
