package netty.reactor.multiReactorMultiThread;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static final String HOST = "127.0.0.1";
    static final int PORT = 1091;
    public static final int READING = 0;
    public static final int WRITING = 1;

    static ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    static ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    static int state = WRITING;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_WRITE);

        while (!Thread.interrupted()) {
            if (state == WRITING) {
                System.out.println("please input msg: ");
                String msg = sc.next();
                writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
                writeByteBuffer.flip();
                socketChannel.write(writeByteBuffer);

                writeByteBuffer.clear();
                socketChannel.register(selector, SelectionKey.OP_READ);
                state = READING;
            } else if (state == READING) {
                socketChannel.read(readByteBuffer);
                readByteBuffer.flip();
                byte[] data = new byte[readByteBuffer.remaining()];
                readByteBuffer.get(data, 0, data.length);
                System.out.println("from server: " + new String(data).trim());

                readByteBuffer.clear();
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                state = WRITING;

            }

        }

    }
}
