package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector 解决的问题：
 * 1、有大量连接时，只处理有数据收发，有事件发生的连接
 * 2、当没有连接时，selector.select(); 阻塞，让出cpu
 */
public class NIOServer {
    private static final int PORT = 9000;
    static ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    static ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    public static void accept(SelectionKey key) throws Exception {
        //todo 强制转成Server Channel
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        /**
         * 转成客户端通道
         *  * 非阻塞模式：accept方法不会阻塞。NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
         *  * 阻塞模式：accept方法会一直在此处阻塞
         *
         *  由于进入此处时，已经有事件发生，因此在这里会直接返回SocketChannel,而不会阻塞
         */
        SocketChannel socketChannel = serverSocketChannel.accept();
        //设置读数据为非阻塞模式。否则会一直阻塞，直到有数据到达
        socketChannel.configureBlocking(false);
        //注册读事件。如果需要给客户端发送数据可以注册写事件
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    public static void read(SelectionKey key) throws Exception {
        //todo 强制转成Client Channel
        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.read(readByteBuffer);
        String resp  = new String (readByteBuffer.array()).trim();
        //readByteBuffer.clear();
        System.out.println("server recv: " + resp);

        String req = "Hello, I am nio server";
        ByteBuffer outBuffer = ByteBuffer.wrap(req.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(outBuffer);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        //设置ServerSocketChannel为非阻塞（连接非阻塞）
        serverSocketChannel.configureBlocking(false);

        //打开Selector
        Selector selector = Selector.open();
        //把ServerSocketChannel注册到selector上，并且select对客户端accept连接操作感兴趣
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");

        while(true) {
            /**
             * 阻塞等待需要处理的事件发生
             */
            selector.select();
            //获取selector中注册的全部事件的 SelectionKey 实例
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //移除，防止重复操作
                iterator.remove();
                //如果通道可连接
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()){
                    //如果通道可读
                    read(key);
                }
            }

        }

    }
}
