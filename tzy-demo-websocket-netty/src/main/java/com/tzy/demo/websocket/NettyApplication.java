package com.tzy.demo.websocket;

import com.tzy.demo.websocket.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/16
 **/
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }
    @Autowired
    private NettyServer nettyServer;
    @Override
    public void run(String... strings) throws Exception {
        nettyServer.run();
    }
}
