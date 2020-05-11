package com.aote.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @Author aote
 * @Date 2020-05-11 00:18
 * @Version 1.0
 * @Description Scattering 将数据写入到buffer时，可采用buffer数组，依次写入
 *              Gathering 从buffer读取数据时，可采用buffer数组依次读
 **/
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception {

        // 使用ServerSocketChannel和SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 假设从客户端接收8个自己
        int messageLength = 8;
        // 循环读取
        while (true) {
            int byteRead = 0;

            while(byteRead < messageLength){
                long l = socketChannel.read(byteBuffers);
                byteRead += l; // 累计读取的字节数
                System.out.println("byteRead="+byteRead);
                // 使用流打印
                Arrays.asList(byteBuffers).stream().map(byteBuffer ->
                        "position="+byteBuffer.position() + " ,limit="+ byteBuffer.limit()
                ).forEach(System.out::println);
            }

            // 将所有buffer进行翻转
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);

            // 将数据读取显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }
            // 将所有buffer clear
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
            /*
            // ByteBuffer::clear原始写法
            Arrays.asList(byteBuffers).forEach(new Consumer<ByteBuffer>() {
                @Override
                public void accept(ByteBuffer byteBuffer) {
                    byteBuffer.clear();
                }
            });
            */
            System.out.println("byteRead="+byteRead + " byteWrite="+byteWrite + " messageLength="+messageLength);

        }
    }

}
