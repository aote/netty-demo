package com.aote.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: Weicf
 * @date: 2020-05-18 14:27
 * @description: 零拷贝服务器接收端
 */
public class IOServer {

    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7005);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(inetSocketAddress);

        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (Exception e){
                    break;
                }
                /**
                 * 倒带 position = 0 mark 作废
                 * public final Buffer rewind() {
                 *     position = 0;
                 *     mark = -1;
                 *     return this;
                 * }
                 */
                byteBuffer.rewind();
            }
        }



    }

}
