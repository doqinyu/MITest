package zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BaseOpTest {
    //private static final String connectString = "101.43.168.8:2181";
    private static final String connectString = "127.0.0.1:2181";
    private static final int sessionTimeout = 2000000;//会话超时时间2s

    private ZooKeeper zkClient = null;

    public void init() throws IOException {
        //注册默认事件处理器
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                //收到事件通知后的回调函数（也就是我们自己的事件处理逻辑）
                System.out.println("watchEvent: " + watchedEvent.getType() + "---" + watchedEvent.getPath());

                try {
                    //每次设成true，是因为每次只能监控一遍
                    zkClient.getChildren("/", true);

                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 新增数据
     */
    public void create() throws InterruptedException, KeeperException {
        String nodeCreated = zkClient.create("/idea", "hello,zk".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    /**
     * 获取子节点
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child: children) {
            System.out.println(child);
        }
    }

    /**
     * 获取znode的数据
     */
    public void getData() throws InterruptedException, KeeperException {
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }

    /**
     * 删除znode
     */
    public void deleteZNode() throws InterruptedException, KeeperException {
        zkClient.delete("/idea", -1);
    }

    /**
     * 设置znode的数据
     */
    public void setData () throws InterruptedException, KeeperException {
        zkClient.setData("/idea", "i miss you".getBytes(StandardCharsets.UTF_8), -1);
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }

    public static void main(String[] args) throws Exception {
        BaseOpTest test = new BaseOpTest();
        test.init();
        //test.create();
        test.getChildren();
    }

}
