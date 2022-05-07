package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

/**
 * 监控Zookeeper树中的数据,主要是异步和事件驱动
 */
public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
    ZooKeeper zk;
    String znode;
    Watcher chainedWatcher;
    boolean dead;
    DataMonitorListener listener;
    byte[] prevData;

    public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;
        zk.exists(znode, true, this, null);
    }

    @Override
    public void processResult(int rc, String s, Object o, Stat stat) {
        boolean exists;
        switch (rc) {
            case KeeperException.CodeDeprecated.Ok:
                exists = true;
                break;
            case KeeperException.CodeDeprecated.NoNode:
                exists = false;
                break;
            case KeeperException.CodeDeprecated.SessionExpired:
            case KeeperException.CodeDeprecated.NoAuth:
                dead = true;
                listener.closing(rc);
                return;

            default:
                zk.exists(znode, true, this, null);
                return;
        }

        byte[] b = null;
        if (exists) {
            try {
                b = zk.getData(znode, false, null);
            } catch (Exception e) {
                System.out.println("e");
                return;
            }
        }

        if (b == null && b!= prevData || (b!=null && !Arrays.equals(prevData, b))) {
            listener.exists(b);
            prevData = b;
        }
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();

        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    System.out.println("SyncConnected");
                    break;
                case Expired:
                    System.out.println("Expired");
                    dead = true;
                    listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
                    break;

            }
        } else {
            if (path != null && path.equals(znode)) {
                zk.exists(znode, true, this, null);
            }
        }

        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
    }
}
