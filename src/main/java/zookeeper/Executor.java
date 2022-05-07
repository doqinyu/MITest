package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 维护 Zookeeper连接
 */
public class Executor implements Watcher, Runnable, DataMonitorListener{


    String znode;
    DataMonitor dm;
    ZooKeeper zk;
    String filename;
    String[] exec;
    Process child;

    public Executor(String hostport, String znode, String filename, String[] exec) throws IOException {
        this.filename = filename;
        this.exec = exec;
        this.znode = znode;
        zk = new ZooKeeper(hostport, 3000, this);
        dm = new DataMonitor(zk, znode, null, this);
    }


    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        dm.process(watchedEvent);
    }

    @Override
    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();

                try {
                    child.waitFor();
                } catch (Exception e) {

                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(data);
                fos.close();
            } catch (Exception e) {
            }

            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }
}
