package juc.park;

import java.util.concurrent.locks.LockSupport;

public class ParkTest {
    private static boolean flag = true;

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * park 挂起线程，unpark 唤醒被挂起的线程
     */
    public static void exampleOne() {
        Thread thread = new Thread(() -> {
            while (flag) {

            }

            System.out.println("before first park");
            LockSupport.park();
            System.out.println("after first park");
            LockSupport.park();
            System.out.println("after second park");

        });

        thread.start();

        flag = false;

        sleep(20);

        System.out.println("before unpark.");
        LockSupport.unpark(thread);
    }



    public static void main(String[] args) {
            exampleOne();
    }

}
