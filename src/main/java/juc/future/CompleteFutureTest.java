package juc.future;

import juc.SmallTool;

import java.util.concurrent.CompletableFuture;

/**
 * supplyAsync 用于开启一个有返回值的异步任务
 * thenCompose 用于连接两个任务。 todo CompletableFuture会把thenCompose后的代码块添加到前一个任务的末尾。
 * thenComposeAsync 用于连接两个任务。 todo CompletableFuture会把thenCompose后的代码块看作一个单独的任务，提交到线程池。
 * thenCombine 用于合并两个并发任务的结果,结果由合并函数BiFunction返回
 * thenApply 用于做任务的后置处理。即任务之间是严格顺序执行的。todo CompletableFuture会将两段代码块封装后放到一个线程中执行
 * thenApplyAsync todo CompletableFuture会将两段代码看成是独立的异步任务。只不过在第二个任务开始前，需要把第一个任务先执行完，并且把第一个任务的结果交给第二个任务??
 * applyToEither 用来获取最先完成的任务
 * exceptionally 用来处理异常
 *
 * thenAccept 会接收前面任务的返回参数，但是没有返回值
 * thenRun 不会接收前面任务的返回参数，也没有返回值
 *
 * https://www.jianshu.com/p/abfa29c01e1d
 * runAsync 无返回值异步任务，会采用内部forkjoin线程池/指定线程池
 * anyOf 只要有一个完成，则完成；有一个抛异常，则携带异常
 * allOf 当所有的future完成时，新的 future同时完成。当某一个方法出现了异常时，新future会在所有future完成时完成，并且包含一个异常
 *
 * join 阻塞，不抛出中断异常
 * get  阻塞，会抛出异常
 * getNow 非阻塞
 *
 */

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
        //join:等待任务结束，并返回任务结果,会抛出异常
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


    public static void test4() {
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白结账、要求开发票");
        CompletableFuture<String> invoice = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员收款500元");
            SmallTool.sleepMillis(100);

            return "500元";
            //todo thenApplyAsync并没有两个线程执行，仍然是ForkJoinPool.commonPool-worker-1 一个线程执行的
        }).thenApplyAsync( money -> {
            SmallTool.printTimeAndThread("服务员开发票，面额500元");
            SmallTool.sleepMillis(200);
            return money + "发票";
        });

        SmallTool.printTimeAndThread("小白接到朋友电话，想一起打游戏");
        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", invoice.join()));
    }


    public static void test5() {
        SmallTool.printTimeAndThread("张三走出餐厅，来到公交站");
        SmallTool.printTimeAndThread("等待 700路或者800路 公交到来");

        /**
         * 如果700路睡眠时间短，那么firstComBus就是700路到了(第一个future的返回值)
         * 如果800路睡眠时间短，那么firstComBus就是800路到了(第二个future的返回值)
         */
        CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread(" 700路公交正在赶来");
            SmallTool.sleepMillis(100);
            return "700路到了";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread(" 800路公交正在赶来");
            SmallTool.sleepMillis(20);
            return "800路到了";
        }), firstComBus -> firstComBus);

        SmallTool.printTimeAndThread(String.format("%s，小白坐车回家", bus.join()));
    }


    public static void test6() {
        SmallTool.printTimeAndThread("张三走出餐厅，来到公交站");
        SmallTool.printTimeAndThread("等待 700路或者800路 公交到来");

        CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread(" 700路公交正在赶来");
            SmallTool.sleepMillis(100);
            return "700路到了";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread(" 800路公交正在赶来");
            SmallTool.sleepMillis(200);
            return "800路到了";
        }), result -> {
            SmallTool.printTimeAndThread(result);
            if (result.startsWith("700")) {
                throw new RuntimeException("撞树了");
            }
            return result;
        }).exceptionally(e-> {
            SmallTool.printTimeAndThread(e.getMessage());
            SmallTool.printTimeAndThread(" 小白叫出租车");
            return "出租车叫到了";
        });
        SmallTool.printTimeAndThread(String.format("%s，小白坐车回家", bus.join()));
    }



    public static void main(String[] args) throws Exception {
        //test1();
        test2();
        //test3();
        //test4();
        //test5();
        //test6();
    }
}
