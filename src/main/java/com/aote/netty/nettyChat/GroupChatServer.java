package com.aote.netty.nettyChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author aote
 * @Date 2020-05-17 10:11
 * @Version 1.0
 * @Description TODO
 **/
public class GroupChatServer {

    private int port;

    public GroupChatServer(int port){
        this.port = port;
    }

    // 编写run方法，处理客户端请求
    public void run() throws Exception {

        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向pipeline里面加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向pipeline里面加入遍码器
                            pipeline.addLast("encoder", new StringDecoder());
                            pipeline.addLast(new GroupChatServerHandler());
                        }

                    });
            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception  {
        new GroupChatServer(8800).run();
    }


}
