package com.aote.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: Weicf
 * @date: 2020-05-22 14:47
 * @description:
 */
public class MyTcpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MyTcpServerHandler());
    }
}
