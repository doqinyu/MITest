package netty.reactor.singleReactorMultiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1090;
    public static final int READING = 0;
    public static final int WRITEING = 1;
    public int state = 1;

    public static ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    public static ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    final Selector selector;
    final SocketChannel socketChannel;
    Scanner sc = new Scanner(System.in);


    public Client() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    public void read() throws Exception {
        socketChannel.read(readByteBuffer);
        readByteBuffer.flip();
        byte[] data = new byte[readByteBuffer.remaining()];
        readByteBuffer.get(data, 0, data.length);
        System.out.println("from server: " + new String(data).trim());
        readByteBuffer.clear();

        state = WRITEING;
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    public void write () throws Exception {
        System.out.println("please put msg: ");
        writeByteBuffer.put(sc.next().getBytes(StandardCharsets.UTF_8));
        writeByteBuffer.flip();
        socketChannel.write(writeByteBuffer);
        writeByteBuffer.clear();

        state = READING;
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public int getState() {
        return state;
    }

    public static void main(String[] args) throws Exception {
      Client client = new Client();

        while (!Thread.interrupted()) {
            if (client.getState() == WRITEING) {
                client.write();

            } else if (client.getState() == READING) {
                client.read();
            }

        }

    }
}
