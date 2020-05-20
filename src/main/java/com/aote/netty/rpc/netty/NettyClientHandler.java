package com.aote.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author: Weicf
 * @date: 2020-05-20 21:22
 * @description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    // 上下文
    private ChannelHandlerContext context;
    // 返回的结果
    private String result;
    // 客户端调用方法时，传入的参数
    private String para;

    /**
     * 与服务器的连接创建后，就会被调用, 这个方法是第一个被调用(1)
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 被调用");
        context = ctx;
    }

    /**
     * 收到服务器的数据后，调用方法 (4)
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用");
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * (3) -> (5)
     * 被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1 被调用");
        context.writeAndFlush(para);
        wait();
        System.out.println("call2 被调用");
        return result;
    }

    /**
     * (2)
     * @param para
     */
    void setPara(String para){
        System.out.println("setPara");
        this.para = para;
    }
}
