package netty.reactor.singleReactorSingleThread;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;


public class Handler implements Runnable {
    ByteBuffer writeByteBuffer = ByteBuffer.allocate(100);
    ByteBuffer readByteBuffer = ByteBuffer.allocate(100);
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static final int READING = 0;
    public static final int WRITING = 1;
    int state;

    Selector selector;
    SocketChannel socketChannel;
    SelectionKey selectionKey;

    public Handler(Selector selector, SocketChannel socketChannel) throws Exception {
        this.state = READING;
        this.selector = selector;
        this.socketChannel = socketChannel;

        this.socketChannel.configureBlocking(false);
        selectionKey = this.socketChannel.register(this.selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == WRITING) {
            write();
        }
    }

    private void read() {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            channel.read(readByteBuffer);
            readByteBuffer.flip();
            byte[] data = new byte[readByteBuffer.remaining()];
            readByteBuffer.get(data, 0, data.length);

            System.out.println("from client: " + new String(data).trim());
            readByteBuffer.clear();

            atomicInteger.incrementAndGet();

        } catch (Exception e) {

        } finally {
            //下一步处理写事件
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            this.state = WRITING;
        }
    }

    private void write() {
        try {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            String msg = " server handle count ---> " + atomicInteger.incrementAndGet();
            writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeByteBuffer.flip();
            channel.write(writeByteBuffer);
            writeByteBuffer.clear();

        } catch (Exception e) {

        } finally {
            //下一步处理读事件
            selectionKey.interestOps(SelectionKey.OP_READ);
            this.state = READING;
        }

    }

    private void process() {
        System.out.println("process....");
    }
}
