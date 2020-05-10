package com.aote.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author: Weicf
 * @date: 2020-05-08 20:45
 * @description: 使用transferForm拷贝图片
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("DSC_2177.JPG");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/aote/Downloads/DSC_2177.JPG");

        // 获取对应的channel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destCChannel = fileOutputStream.getChannel();

        // 使用transferForm完成拷贝，直接从源开始拷贝，理解sourceChannel和源图片关联
        destCChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
    }

}
