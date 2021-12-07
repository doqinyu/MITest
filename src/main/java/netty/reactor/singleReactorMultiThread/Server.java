package netty.reactor.singleReactorMultiThread;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server {
    public static int PORT = 1090;

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Reactor reactor = new Reactor(selector, serverSocketChannel, PORT);
        reactor.run();

    }
}
