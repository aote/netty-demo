package com.aote.netty.nettyChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @Author aote
 * @Date 2020-05-17 11:00
 * @Version 1.0
 * @Description TODO
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler {

    /**
     * 定义一个channel组，管理所有channel
     * GlobalEventExecutors.INSTANCE是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示连接建立，一旦连接，第一个被执行
     * 将当前channel加入到channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 将该客户加入聊天的信息推送给其他在线的客户端
         * writeAndFlush该方法会遍历channelGroup并发送消息，不需要自己遍历
         */
        channelGroup.writeAndFlush("[客户端] "+channel.remoteAddress()+" 加入聊天");
        channelGroup.add(channel);
    }

    /**
     * 表示channel处于活动状态，提示xx上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" 上线了");
    }

    /**
     * 表示channel处于非活动状态，提示xx离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" 离线了");
    }

    // 断开连接，将xx客户离开信息推送给当前在线客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 离开了");
        System.out.println("channelGroup size="+channelGroup.size());
    }

    /**
     * 读取数据，发送给所有客户端
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取当前channel
        Channel channel = ctx.channel();
        // 遍历channelGroup，根据不同情况会送不同的消息
        channelGroup.forEach(item -> {
            // item不是当前channel，转发消息
            if(channel != item){
                item.writeAndFlush("[客户]"+channel.remoteAddress()+" 说："+msg+"\n");
            } else {
                item.writeAndFlush("[我] 说了："+msg+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 如果发生异常，关闭通道
        ctx.close();
    }
}
