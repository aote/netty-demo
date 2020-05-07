package com.aote.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author aote
 * @Date 2020-05-08 00:18
 * @Version 1.0
 * @Description TODO
 **/
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {

        String s = "hello world!";
        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/aote/Downloads/hello.md");
        // 通过fileOutputStream获取对应的FileChannel，fileChannel真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将s放入到byteBuffer，这里是对byteBuffer进行写操作
        byteBuffer.put(s.getBytes());
        // 对byteBuffer进行反转，给FileChannel读
        byteBuffer.flip();
        // 将byteBuffer写入到FileChannel
        fileChannel.write(byteBuffer);
        // 关闭流
        fileOutputStream.close();
    }

}
