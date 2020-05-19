package com.tzy.demo.websocket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author tangzanyong
 * @description 接收处理并响应客户端WebSocket请求的核心业务处理类
 * @date 2020/5/16
 **/
@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    @Value("${netty.websocket.ip}")
    private static String ip;
    /**
     * websocket监听端口
     */
    @Value("${netty.websocket.port}")
    private static int port;

    private WebSocketServerHandshaker handshaker;
    private static final String WEB_SOCKET_URL="ws://" + ip + ":" + port + "/webSocket";

//   @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //super.channelRead(ctx, msg);
//    }

    //服务端处理客户端websocket请求的时候调用
    // @Override
//    protected void messageReceived(ChannelHandlerContext context, Object msg) throws Exception {
//        //处理客户端向服务器端发起http握手请求的任务
//         if (msg instanceof FullHttpRequest){
//             handHttpRequest(context,(FullHttpRequest) msg);
//         }else if (msg instanceof WebSocketFrame){  //处理websocket连接业务
//             handWebsocketFrame(context,(WebSocketFrame)msg);
//         }
//    }

    /**
     * 服务端处理客户端WebSocket请求的核心方法
     * @param context
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, Object msg) throws Exception {
        //处理客户端向服务器端发起http握手请求的任务
        if (msg instanceof FullHttpRequest){
            handHttpRequest(context,(FullHttpRequest) msg);
        //处理websocket连接 业务
        }else if (msg instanceof WebSocketFrame){
            handWebsocketFrame(context,(WebSocketFrame)msg);
        }
    }
    /**
     * 处理客户端与服务端之间的webSocket业务
     * @param ctx
     * @param frame
     */
    private void handWebsocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        //判断是否是关闭webSocket的指令
//        if(frame instanceof CloseWebSocketFrame){
//            handshaker.close(ctx.channel(),(CloseWebSocketFrame)frame.retain());
//        }
        if (frame instanceof CloseWebSocketFrame) {
            ctx.channel().close();
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            log.debug("接收到ping消息");
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息(判断是否是二进制消息，如果是二进制消息，则抛出异常)
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("目前仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported(不支持的消息)", frame.getClass().getName()));
        }

        //获取客户端向服务端发送的消息
        String request = ((TextWebSocketFrame) frame).text();
        log.info("服务端收到客户端的消息: " + request);

        // 返回应答消息
        //TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString()  + "：" + request);
        String response = new Date().toString()
                + ctx.channel().id() +
                " ===>>> " + request;
        TextWebSocketFrame tws = new TextWebSocketFrame(request);

        // 群发，服务器向每个连接上来的客户端群发消息
        NettyServer.channelGroup.writeAndFlush(tws);
        log.debug("群发消息完成. 群发的消息为: {}", response);
        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);
    }
    /**
     * 处理客户端向服务端发起http握手请求的业务
     * @param ctx
     * @param req
     */
    private void handHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        // 非websocket的http握手请求处理,如果HTTP解码失败，返回HHTP异常decoderResult()
        if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            log.warn("非websocket的http握手请求");
            return;
        }
        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                WEB_SOCKET_URL, null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            // 响应不支持的请求
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            log.warn("不支持的请求");
        } else {
            handshaker.handshake(ctx.channel(), req);
            log.debug("正常处理");
        }
    }

    /**
     * 服务端主动向客户端发送消息
     * @param ctx
     * @param req
     * @param res
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 不成功的响应, 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            log.warn("不成功的响应");
        }
        // 服务端向客户端发送数据
        ChannelFuture f = ctx.channel().writeAndFlush(res);
//        if (res.getStatus().code() != 200) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
        if (!HttpUtil.isKeepAlive(req) ||  res.status().code() != 200) {
            // 如果是非Keep-Alive，或不成功都关闭连接
            f.addListener(ChannelFutureListener.CLOSE);
            log.info("websocket连接关闭");
        }
    }


    //客户端与服务端创建连接的时候调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 将channel添加到channel group中
        NettyServer.channelGroup.add(ctx.channel());
        log.info("客户端与服务端连接开始......");
    }
    //客户端与服务器断开连接调用
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 从channel group中移除这个channel
        NettyServer.channelGroup.remove(ctx.channel());
        log.info("客户端与服务端连接关闭......");
    }

    //服务器接收客户端发送过来的数据结束之后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 清空数据
        ctx.flush();
        log.info("flush数据 {}", ctx.name());
    }

    //工程出现异常的时候调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常堆栈
        cause.printStackTrace();
        // 主动关闭连接
        ctx.close();
        log.error("WebSocket连接异常");
    }
}
