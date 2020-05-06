package com.aote.nio;

import java.nio.IntBuffer;

/**
 * @Author aote
 * @Date 2020-05-06 22:57
 * @Version 1.0
 * @Description TODO
 **/
public class BasicBuffer {

    public static void main(String[] args) {
        // 举例说明Buffer的使用
        // 创建一个Buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer中存放数据
        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);

        // 也可以通过循环放
        for(int i=0;i<intBuffer.capacity();i++){

        }

        // 从buffer中读取数据
        // 将buffer转换，读写切换
        intBuffer.flip();
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }

}
