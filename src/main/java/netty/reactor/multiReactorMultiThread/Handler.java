package netty.reactor.multiReactorMultiThread;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Handler implements Runnable {
    final int subReactorIndex;
    final Selector selector;
    final SelectionKey selectionKey;
    final SocketChannel socketChannel;
    public static final int READING = 0;
    public static final int WRITING = 1;
    public static final int PROCESSING = 2;
    ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);
    int state = 0;

    //多线程处理业务逻辑
    int workCount = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(workCount);
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public Handler(Selector selector, SelectionKey selectionKey, SocketChannel socketChannel, int index) throws Exception {
        this.subReactorIndex = index;
        this.selector = selector;
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
        this.selectionKey.attach(this);
    }


    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == WRITING) {
            write();
        } else {
            System.out.println("subReactor [" + subReactorIndex + "] state = processing");
        }
    }

    private void read() {
        realRead();

        //业务异步处理
        executorService.submit(() -> {
            processBusiness();
        });

        state = PROCESSING;
    }

    private void write() {
        realWrite();
        state = READING;
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private void realRead() {
        try {
            socketChannel.read(readByteBuffer);
            readByteBuffer.flip();
            byte[] data = new byte[readByteBuffer.remaining()];
            readByteBuffer.get(data, 0, data.length);
            System.out.println("from client: " + new String(data).trim());

            readByteBuffer.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void realWrite() {

        try {
            String msg = "subReacor[" + subReactorIndex + "] server replay: " + atomicInteger.incrementAndGet();
            writeByteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
            writeByteBuffer.flip();
            socketChannel.write(writeByteBuffer);
            writeByteBuffer.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void processBusiness() {
        try {
            Thread.sleep(1000);
            System.out.println("subReacor[" + subReactorIndex + "] processBusiness");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = WRITING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }

    }
}
