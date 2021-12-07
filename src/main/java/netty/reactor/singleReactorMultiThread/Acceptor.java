package netty.reactor.singleReactorMultiThread;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable{

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) throws Exception {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }


    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel) {
                new MultiThreadHandler(this.selector, socketChannel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
