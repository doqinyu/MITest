package netty.reactor.singleReactorSingleThread;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 连接事件就绪，处理连接事件
 */
public class Acceptor implements Runnable{
    Selector selector;
    ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel) {
                new Handler(this.selector, socketChannel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
