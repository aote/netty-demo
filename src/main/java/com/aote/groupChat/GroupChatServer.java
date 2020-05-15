package com.aote.groupChat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author: Weicf
 * @date: 2020-05-15 11:51
 * @description: 群聊
 */
public class GroupChatServer {

    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    // 初始化工作
    public GroupChatServer() {
        try{
            // 得到选择器
            selector = Selector.open();
            //
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 注册到listenCharlene
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 监听
    public void listen(){
        try {
            while(true) {

                int count = selector.select(2000);
                // 有事件处理
                if(count>0){
                    // 遍历得到的selectedKeys
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                    while (keyIterator.hasNext()){
                        // 取出selectionKey
                        SelectionKey selectionKey = keyIterator.next();

                        // 监听到accept事件
                        if(selectionKey.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            // 将该sc注册到selector
                            sc.register(selector,SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress()+" 上线了...");
                        }

                        // 通道发生read事件
                        if(selectionKey.isReadable()){
                            // 处理读方法
                            this.readData(selectionKey);
                        }

                        // 删除当前的key，防止重复处理
                        keyIterator.remove();
                    }

                } else {
                    System.out.println("等待...");
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {

        }
    }

    // 读取客户端消息
    private void readData(SelectionKey selectionKey){
        // 定义一个socketChannel
        SocketChannel channel = null;
        try {
            // get associate channel
            channel = (SocketChannel) selectionKey.channel();
            // create byteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // process by count value
            if(count > 0){
                // translate buffer's data to String
                String msg = new String(buffer.array());
                // output this msg
                System.out.println("from client："+msg);

                // send the message to other clients (except oneself),create another method to process
                this.sendMessageToOtherClients(msg,channel);

            }
        } catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress()+" offline");
                // needs close the channel
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // transmit the message to other clients
    private void sendMessageToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息");
        // travers all of the registd client on the selector,except oneself
        for(SelectionKey  selectionKey : selector.keys()){
            // get homologous socketChannel by selectionKey
            Channel targetChannel = selectionKey.channel();
            // exclude oneself
            if(targetChannel instanceof  SocketChannel && targetChannel != self){
                // transformation
                SocketChannel dest = (SocketChannel) targetChannel;
                // store the message to buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // write buffer's data to channel
                dest.write(buffer);
            }

        }

    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
