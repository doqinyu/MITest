package juc.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    private ExecutorService executorService = new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(3000, true),
            new ThreadFactoryBuilder().setNameFormat("batchQueryVideoCoeService-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    public void testSubmit() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        });
    }

    public static void main(String[] args) {

    }
}
