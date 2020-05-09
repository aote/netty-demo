package com.aote.nio;

import java.nio.ByteBuffer;

/**
 * @author: Weicf
 * @date: 2020-05-08 21:03
 * @description:
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for(int i=0;i<64;i++){
            byteBuffer.put((byte)i);
        }

        byteBuffer.flip();

        // 得到一个只读buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }

}
