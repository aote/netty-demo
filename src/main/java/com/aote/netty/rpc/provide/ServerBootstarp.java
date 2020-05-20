package com.aote.netty.rpc.provide;

import com.aote.netty.rpc.netty.NettyServer;

/**
 * @author: Weicf
 * @date: 2020-05-20 21:16
 * @description:ServerBootstrap 会启动一个服务提供者，就是 NettyServer
 */
public class ServerBootstarp {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7007);
    }
}
