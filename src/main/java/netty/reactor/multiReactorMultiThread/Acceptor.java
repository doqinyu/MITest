package netty.reactor.multiReactorMultiThread;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 多work连接事件Acceptor，处理连接事件
 */
public class Acceptor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    // cpu 线程数相同多的work线程
    //int workCount = Runtime.getRuntime().availableProcessors();
    int workCount = 2;
    Selector[] selectors = new Selector[workCount];
    //一个Selector 代表一个SubReactor
    SubReactor[] subReactorList = new SubReactor[workCount];
    //SubReactor的处理线程
    Thread[] threads = new Thread[workCount];
    //轮询使用SubReactor的下标️索引
    int nextSubReactorIndex = 0;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;

        for (int i=0; i< subReactorList.length; i++) {
            selectors[i] = Selector.open();
            subReactorList[i] = new SubReactor(selectors[i], i);
            threads[i] = new Thread(subReactorList[i]);
            //线程启动，启动后，执行SubReactor的run方法
            threads[i].start();
        }
    }

    @Override
    public void run() {
            try {
                //处理连接事件
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (null != socketChannel) {
                    //选择一个 SubReactor 处理该连接事件
                    socketChannel.configureBlocking(false);
                    subReactorList[nextSubReactorIndex].registering(true);
                    /**
                     * 注意一个selector在select时是无法注册新事件的，因此这里要先暂停select方法触发的程序段
                     */

                    //使一个阻塞住的selector操作立即返回
                    selectors[nextSubReactorIndex].wakeup();
                    //当前客户端通道向selectors[nextSubReactorIndex]注册一个读事件，返回key
                    SelectionKey selectionKey = socketChannel.register(selectors[nextSubReactorIndex], SelectionKey.OP_READ);
                    selectors[nextSubReactorIndex].wakeup();
                    subReactorList[nextSubReactorIndex].registering(false);

                    selectionKey.attach(new Handler(selectors[nextSubReactorIndex], selectionKey, socketChannel, nextSubReactorIndex));

                    //越界后重新分配
                    if (++nextSubReactorIndex == workCount) {
                        nextSubReactorIndex = 0;
                    }
                }

            } catch (Exception e) {

            }
    }
}
