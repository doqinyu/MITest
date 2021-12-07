package netty.reactor.multiReactorMultiThread;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class MainReactor implements Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    final SelectionKey selectionKey;
     final int PORT;


    public MainReactor(Selector selector, ServerSocketChannel serverSocketChannel, int port) throws IOException {
        this.PORT = port;
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selectionKey = serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        selectionKey.attach(new Acceptor(selector, serverSocketChannel));


    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //mainReactor只关联Acceptor，处理连接事件
                    SelectionKey selectionKey = iterator.next();
                    Runnable acceptor = (Runnable)selectionKey.attachment();
                    acceptor.run();
                    iterator.remove();
                }

            } catch (Exception e) {

            }

        }
    }

}
