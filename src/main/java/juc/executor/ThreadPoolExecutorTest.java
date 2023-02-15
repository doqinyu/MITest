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

    public static void testRetry() {
        int i = 1;
        retry:
        for (int  j = 0;j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (i == 0) {
                    System.out.println("i == 0, continue");
                    continue retry;
                }
                if (i == 1) {
                    System.out.println("i == 1, break");
                    break retry;
                }

                System.out.println("kkkkkkkkkkk");
            }
            System.out.println("jjjjjjjjjjjj");
        }
    }

    public static void main(String[] args) {
        testRetry();
    }
}
