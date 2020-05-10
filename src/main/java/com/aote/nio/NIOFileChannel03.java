package com.aote.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: Weicf
 * @date: 2020-05-08 20:11
 * @description: 使用一个buffer进行文件读写
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("M.txt");
        FileChannel fileInputChannel = fileInputStream.getChannel();

        // 可以理解为FileOutputStream和E://M.txt文件关联，然后又创建了一个FileChannel
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/aote/Downloads/M.txt");
        FileChannel fileOutChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

        // 循环读取
        while (true) {

            /**
             *     public final Buffer clear() {
             *         position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             *     }
             *     这里有个重要的操作，清空buffer
             */
            byteBuffer.clear();
            int read = fileInputChannel.read(byteBuffer);
            // -1 表示读完
            if(read == -1) {
                break;
            }
            // 将buffer中的数据写入到fileOutChannel，这里需要翻转
            byteBuffer.flip();
            fileOutChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();

    }

}
