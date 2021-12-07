package netty.reactor.singleReactorSingleThread;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
    static ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9000;
    public static final int READING = 0;
    public static final int WRITING = 1;

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        int state = WRITING;

        //发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while (!Thread.interrupted()) {
            if (state == WRITING) {
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                System.out.println("please put msg:");
                String msg = scan.next();
                writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
                writeByteBuffer.flip();
                socketChannel.write(writeByteBuffer);
                writeByteBuffer.clear();

                state = READING;
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else if (state == READING) {

                socketChannel.read(readByteBuffer);
                readByteBuffer.flip();
                byte[] data = new byte[readByteBuffer.remaining()];
                readByteBuffer.get(data, 0, data.length);
                System.out.println("from server: " + new String (data).trim());
                readByteBuffer.clear();

                state = WRITING;
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            }
        }
    }
}
