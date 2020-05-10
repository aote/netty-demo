package com.aote.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author aote
 * @Date 2020-05-10 11:18
 * @Version 1.0
 * @Description MappedByteBuffer 可以直接使用堆外内存进行文件修改，不用再经过操作系统拷贝一次
 **/
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("M.txt","rw");
        // 获取对应的通道
        FileChannel randomAccessFileChannel = randomAccessFile.getChannel();

        /**
         * 参数1.读写模式
         * 参数2.可以直接修改的起始位置
         * 参数3.映射到内存的大小（不是索引位置），即可以映射到几个字节到内存
         * MappedByteBuffer实际类型是DirectByteBuffer
         */
        MappedByteBuffer byteBuffer = randomAccessFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        byteBuffer.put(0,(byte) 'B');
//        byteBuffer.put("好".getBytes(),0,3);

        randomAccessFile.close();
        System.out.println("修改成功");
    }

}
