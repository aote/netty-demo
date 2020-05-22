package com.aote.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.logging.SocketHandler;

/**
 * @author: Weicf
 * @date: 2020-05-22 14:56
 * @description:
 */
public class MyTcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据，hello server + 编号
        for(int i=0;i<10;i++) {
            ByteBuf buffer = Unpooled.copiedBuffer("hello server "+i, Charset.forName("utf-8"));
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);
        String message = new String(buffer,Charset.forName("utf-8"));
        System.out.println("客户端接收到消息=" + message);
        System.out.println("客户端接收消息数量=" + (++this.count));
    }

}
