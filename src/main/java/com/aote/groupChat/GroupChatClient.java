package com.aote.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author: Weicf
 * @date: 2020-05-15 14:43
 * @description:
 */
public class GroupChatClient {

    // define attributes
    private final String HOST = "127.0.0.1";
    private final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            // 得到选择器
            selector = Selector.open();
            //
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // register socketChannel to selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            this.username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(this.username+" is ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向服务器发送消息
    private void sendMessageToServer(String message){
        message = username+" say "+message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取服务器回复的消息
    private void readMessageByServer(){
        try {
            int readChannel = selector.select();
            // 有事件发生的通道
            if(readChannel > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    if(selectionKey.isReadable()){
                        // 得到相关的通道
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        // 得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // read
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg);
                    }
                }
                keyIterator.remove();
            } else {
//                        System.out.println("没有可用通道");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();

        // 每隔3秒从服务器端读取数据
        new Thread( () -> {
            while (true) {
                groupChatClient.readMessageByServer();
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            groupChatClient.sendMessageToServer(s);
        }

    }

}
