package com.aote.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: Weicf
 * @date: 2020-05-08 20:03
 * @description: NIO读文件
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {
        // 创建文件的输入流
        File file = new File("D:\\Hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 通过fileInputStread获取对应的FileChannel(实际类型是FileChannelImpl)
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据读入到缓冲区
        fileChannel.read(byteBuffer);

        // 将缓冲区的字节转数据换成字符串
        System.out.println(new String(byteBuffer.array()));

        // 关闭流
        fileInputStream.close();
    }

}
