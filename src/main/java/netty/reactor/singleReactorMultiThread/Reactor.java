package netty.reactor.singleReactorMultiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Reactor implements Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    final int PORT;
    SelectionKey selectionKey;

    public Reactor(Selector selector, ServerSocketChannel serverSocketChannel, int port) throws Exception {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.PORT = port;

        this.serverSocketChannel.socket().bind(new InetSocketAddress((this.PORT)));
        this.serverSocketChannel.configureBlocking(false);

        selectionKey = this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        //关联Acceptor
        selectionKey.attach(new Acceptor(this.selector, this.serverSocketChannel));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void dispatch(SelectionKey sk) {
        Runnable runnable = (Runnable)sk.attachment();
        if (null != runnable) {
            runnable.run();
        }
    }
}
