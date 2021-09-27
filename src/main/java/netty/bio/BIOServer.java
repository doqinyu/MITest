package netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class BIOServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("the time server start in port: " + PORT);
            Socket socket = null;
            while(true) {
                System.out.println("wait for conn");
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();

                /**
                 * 伪NIO，使用线程池处理请求.总共1000个请求，线程池数量100，等待队列500（BlockingQueue），还有400个请求,可设置拒绝策略
                 * 一般高并发访问的时候，比如大学抢课，会出现拒绝连接，或者连接超时
                 */
                //Executor executor = new ThreadPoolExecutor()
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != server) {
                System.out.println("the time server close ");
                server.close();
            }
        }
    }
}
