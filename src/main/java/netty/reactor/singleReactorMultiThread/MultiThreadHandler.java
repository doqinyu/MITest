package netty.reactor.singleReactorMultiThread;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程处理读写业务逻辑
 */
public class MultiThreadHandler implements Runnable {
    public ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    public ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
    public static final int READING = 0;
    public static final int WRITING = 1;
    public static final int PROCESSING = 2;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    int state = 0;

    final Selector selector;
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;

    //多线程处理业务逻辑
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public MultiThreadHandler(Selector selector, SocketChannel socketChannel) throws Exception {
        this.selector = selector;
        this.socketChannel = socketChannel;

        this.socketChannel.configureBlocking(false);
        selectionKey = this.socketChannel.register(this.selector, SelectionKey.OP_READ);
        this.selectionKey.attach(this);
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == WRITING) {
            write();
        }
    }

    public void read() {
        //读取数据流数据
        realRead();

        executorService.submit(() -> {
            //异步业务处理
            processAndHandOff();
        });

        state = PROCESSING;
    }

    public void realRead() {
        try {
            socketChannel.read(readByteBuffer);
            readByteBuffer.flip();

            byte[] data = new byte[readByteBuffer.remaining()];
            readByteBuffer.get(data, 0, data.length);
            System.out.println("from client: " + new String(data).trim()
                    + ", readByteBuffer.position = " + readByteBuffer.position()
                    + ", readByteBuffer.limit = " + readByteBuffer.limit()
                    + ", readByteBuffer.capacity = " + readByteBuffer.capacity());

            readByteBuffer.clear();

            atomicInteger.incrementAndGet();

        } catch (Exception e) {
            System.out.println("realRead ===>" + e);
        }

    }

    public synchronized void processAndHandOff() {
        try {
            Thread.sleep(500);
            System.out.println("processAndHandOff: decode -> compute -> encode ");
        } catch (Exception e) {
            System.out.println("processAndHandOff ===>" + e);
        } finally {
            state = WRITING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    public void write() {
        //写出数据流
        readWrite();

        selectionKey.interestOps(SelectionKey.OP_READ);
        state = READING;
    }

    public void readWrite() {
        try {
            String msg = "server to client: atomicInteger ---> " + atomicInteger.incrementAndGet();
            writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeByteBuffer.flip();
            socketChannel.write(writeByteBuffer);

            System.out.println("writeByteBuffer.position = " + writeByteBuffer.position()
                    + ", writeByteBuffer.limit = " + writeByteBuffer.limit()
                    + ", writeByteBuffer.capacity = " + writeByteBuffer.capacity());

            writeByteBuffer.clear();

        } catch (Exception e) {
            System.out.println("readWrite ===> " + e);
        }
    }

}
