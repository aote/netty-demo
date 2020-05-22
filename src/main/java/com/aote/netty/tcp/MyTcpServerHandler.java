package com.aote.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author: Weicf
 * @date: 2020-05-22 14:48
 * @description:
 */
public class MyTcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);

        // 将buffer转换成字符串
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到消息量=" + (++this.count));

        // 服务器会送数据给客户端，回送一个随机id
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(),Charset.forName("utf-8"));
        channelHandlerContext.writeAndFlush(responseByteBuf);
    }

}
