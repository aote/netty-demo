package com.aote.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author aote
 * @Date 2020-05-14 21:25
 * @Version 1.0
 * @Description 客户端
 **/
public class NIOClient {

    public static void main(String[] args) throws Exception {

        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器ip和端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        // 连接服务器
        if(!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞");
            }
        }

        // 如果连接成功就发送数据
        String s = "hello,weicf!";
        // 产生一个字节数组到buffer中
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        // 将buffer中的数据写入channel
        socketChannel.write(buffer);
        // 停止
//        System.in.read();


    }

}
