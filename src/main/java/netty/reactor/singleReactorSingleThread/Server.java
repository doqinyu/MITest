package netty.reactor.singleReactorSingleThread;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 单Reactor单线程模型
 */
public class Server {
    private static final int PORT = 9000;
    Selector selector;
    ServerSocketChannel serverSocketChannel;


    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.selector = Selector.open();
        server.serverSocketChannel = ServerSocketChannel.open();
        Reactor reactor = new Reactor(server.selector, server.serverSocketChannel, PORT);
        reactor.run();
    }

}
