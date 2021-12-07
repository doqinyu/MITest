package netty.reactor.singleReactorSingleThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
/**
 * 等待事件到来，分发事件处理
 */
public class Reactor implements Runnable {
    int port = 9000;
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    SelectionKey selectionKey;

    public Reactor(Selector selector, ServerSocketChannel serverSocketChannel, int port) throws Exception {
        this.port = port;
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;

        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        selectionKey = this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        //attach Acceptor 处理新连接
        selectionKey.attach(new Acceptor(selector, serverSocketChannel));

    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                //System.out.println("server start select...");
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void dispatch(SelectionKey selectionKey) {
        /**
         * 如果是连接事件，Runnable 就是Acceptor
         * 如果是IO读写事件，Runnable 就是Handler
         */
        Runnable runnable = (Runnable)selectionKey.attachment();
        if (null != runnable) {
            runnable.run();
        }
    }
}
