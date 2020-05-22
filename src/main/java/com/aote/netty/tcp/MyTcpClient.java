package com.aote.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: Weicf
 * @date: 2020-05-22 14:56
 * @description:
 */
public class MyTcpClient {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    // 自定义初始化类
                    .handler(new MyTcpClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost",7010).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }

}
