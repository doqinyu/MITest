package zookeeper;


import java.io.IOException;

public class SimpleDemoTest {
    public static final String HOSTPOSR = "101.43.168.8:2181";
    public static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        String znode = "text-zk";
        String filename = "";
        String[] exec = {"","",""};
        System.arraycopy(args, 3, exec, 0, exec.length);

        try {
            new Executor(HOSTPOSR, znode, filename, exec).run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
