package netty.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * 优点：模型简单，编码简单
 * 缺点：性能瓶颈，请求数和线程数 N:N关系
 *      高并发情况下，CPU切换线程上下文损耗大
 *
 * 案例：web服务器Tomcat7之前，都是使用BIO，7 之后就使用NIO
 * 改进：伪NIO，使用线程池处理请求
 */
public class BIOClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("I am client");
            String resp = in.readLine();
            System.out.println("server resp: " + resp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (null != socket) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
