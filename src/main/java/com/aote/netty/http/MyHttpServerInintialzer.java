package com.aote.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: Weicf
 * @date: 2020-05-18 17:23
 * @description:
 */
public class MyHttpServerInintialzer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * 加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
         * HttpServerCodec 说明
         * 1. HttpServerCodec 是netty 提供的处理http的 编-解码器
         */
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("MyHttpServerHandler",new MyHttpServerHandler());

        System.out.println("ok~~~");

    }
}
