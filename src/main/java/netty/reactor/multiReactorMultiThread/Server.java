package netty.reactor.multiReactorMultiThread;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server {
    static final int PORT = 1091;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        MainReactor mainReactor = new MainReactor(selector, serverSocketChannel, PORT);
        mainReactor.run();
    }
}
