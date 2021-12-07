package netty.reactor.multiReactorMultiThread;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;

public class SubReactor implements Runnable {
    private final int index;
    private final Selector selector;
    private boolean stop = false;//selector 是否暂停标识

    public SubReactor(Selector selector, int index) throws IOException {
        //每一个SubReactor 一个Selector
        this.selector = selector;
        this.index = index;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println(index + " 号SubReactor等待注册中...");
            while (!Thread.interrupted() && !stop) {

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
        //SubReactor只关联Handler,处理读写事件
        Runnable runnable = (Runnable) key.attachment();
        if (null != runnable) {
            runnable.run();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
        System.out.println("subReactor[" + index + "] stop = " + stop);
    }

}
