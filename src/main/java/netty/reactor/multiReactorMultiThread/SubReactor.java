package netty.reactor.multiReactorMultiThread;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;

public class SubReactor implements Runnable {
    private final int index;
    private final Selector selector;
    private boolean register = false;

    public SubReactor(Selector selector, int index) throws IOException {
        //每一个SubReactor 一个Selector
        this.selector = selector;
        this.index = index;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println(index + " 号SubReactor等待注册中...");
            while (!Thread.interrupted() && !register) {

                try {
                    selector.select();

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        dispatch(iterator.next());
                        iterator.remove();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    public void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable)key.attachment();
        if (null != runnable) {
            runnable.run();
        }
    }

    public void registering(boolean register) {
        this.register = register;
        System.out.println("subReactor["+ index + "] register = " + register);
    }

}
