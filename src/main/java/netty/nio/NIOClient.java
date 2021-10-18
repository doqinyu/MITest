package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NIOClient {
    static ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9000;

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

        //发送数据给服务端
        Scanner scan = new Scanner(System.in);
        System.out.println("please put msg:");
        while (scan.hasNext()) {
            String msg = scan.next();
            writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeByteBuffer.flip();
            socketChannel.write(writeByteBuffer);
            writeByteBuffer.clear();
            System.out.println("please put msg:");
        }
    }
}
