package com.aote.netty.rpc.netty;

import com.aote.netty.rpc.customer.ClientBootstrap;
import com.aote.netty.rpc.provide.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Weicf
 * @date: 2020-05-20 21:04
 * @description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        /**
         * 客户端在调用服务端的api时，需要定义一个协议
         * 比如我们要求每次发消息都是必选以某个字符串开头 "HelloService#hello#你好"
         */
        if(msg.toString().startsWith(ClientBootstrap.providerName)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
