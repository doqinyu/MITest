package test3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件类
 */
public class File {

    /**
     * 文件编号
     */
    int no;

    /**
     * 文件内容
     */
    List<String> contentList;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));

    Random random = new Random();

    public void readFromFile () {
        //从文件中读取内容到contentList
    }
    public void start() {
        //在线程池中提交每行文件的写入任务
        for (int i = 0; i <= contentList.size(); i++) {
            threadPoolExecutor.execute(new FileTask(no, i, i == contentList.size() ? null : contentList.get(i), i == contentList.size()));
            try {
                Thread.sleep(random.nextLong());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
