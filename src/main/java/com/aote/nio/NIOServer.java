package com.aote.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author aote
 * @Date 2020-05-14 19:29
 * @Version 1.0
 * @Description 服务器端
 **/
public class NIOServer {

    public static void main(String[] args) throws Exception {
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个selector对象
        Selector selector = Selector.open();

        // 绑定一个端口，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while(true){
            // 等待1秒，如果没有事件发生就继续
            if(selector.select(1000)==0){ // ==0代表没有事件发生
                System.out.println("服务器等待1秒，无连接");
                continue;
            }

            // 如果返回的>0，就获取到相关的selectionKeys's Set
            // 1.如果>0表示已经获取到关注的事件
            // 2.selector.selectedKeys()返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历Set<SelectionKey>
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while(keyIterator.hasNext()){
                // 获取SelectionKey
                SelectionKey key = keyIterator.next();
                /**
                 * 根据key对应的通道发生的事件做相应处理，
                 * 如果是OP_ACCEPT，代表有新的客户端连接
                 */
                if(key.isAcceptable()){
                    // 给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 " + socketChannel.hashCode());
                    /**
                     * 将当前socketChannel注册到selector,
                     * 关注事件为OP_READ，同时关联一个buffer
                     */
                    socketChannel.register(selector,SelectionKey.OP_READ,
                            ByteBuffer.allocate(1024));

                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                }

                // 发生读事件
                if(key.isReadable()){
                    // 通过key反向获取到对应的channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("from 客户端" + new String(byteBuffer.array()));
                }
                // 从集合中移除当前的selectorKey，防止重复操作
                keyIterator.remove();
            }
        }


    }

}
