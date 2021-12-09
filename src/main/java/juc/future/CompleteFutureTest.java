package juc.future;

import juc.SmallTool;

import java.util.concurrent.CompletableFuture;

/**
 * CompletionStage
 *
 * thenCompose : 在前一个任务完成，有结果后，下一个任务才会触发
 */
public class CompleteFutureTest {

    public static void test1() throws Exception {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1 end");
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2 end");
        });

        /**
         *  不加这一行，则什么都不会输出
         *  这是
         *  todo 因为future线程是一个守护线程，当主线程结束时，所有线程都结束
         */
        //future.get();

        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2).thenRun(() -> {
            System.out.println("future end");

            throw new RuntimeException("future exception");
        }).handle((value, throwable) -> {
            throwable.printStackTrace();
            return value;
        });

        future.get();
    }

    //thenCompose
    public static void test2() {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);

            return "番茄炒蛋" ;
        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员打饭");
            SmallTool.sleepMillis(100);
            return dish + " + 米饭";
        }));

        SmallTool.printTimeAndThread("小白在打王者");
        //join:等待任务结束，并返回任务结果
        SmallTool.printTimeAndThread(String.format("%s, 小白开吃", cf1.join()));
    }

    //thenCombine: 把上个任务和这个任务一起执行
    public static void test3() {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员蒸饭");
            SmallTool.sleepMillis(200);

            return "番茄炒蛋" ;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(100);
            return "米饭";

        }), (dish, race) -> {
            //todo 执行这个任务的线程与执行cf1的线程一致？？？？
            SmallTool.printTimeAndThread("服务员打饭");
            SmallTool.sleepMillis(100);
            return String.format("%s + %s 好了", dish, race);
        });

        SmallTool.printTimeAndThread("小白在打王者");
        //join:等待任务结束，并返回任务结果
        SmallTool.printTimeAndThread(String.format("%s, 小白开吃", cf1.join()));
    }

    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        test3();
    }
}
