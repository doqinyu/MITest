package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatTest extends Thread{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String name;
    private String dateStr;
    private boolean sleep;

    public SimpleDateFormatTest(String name,String dateStr,  boolean sleep) {
        this.name = name;
        this.dateStr = dateStr;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        Date date = null;

        if (sleep) {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(name + " : date: " + date);
    }

    public static void main(String[] args) {
        //simpleDateFormat.parse()
        ExecutorService executor = Executors.newCachedThreadPool();
        //A 会sleep 2s 后开始执行sdf.parse()
        executor.execute(new SimpleDateFormatTest("A", "2000-01-13", true));
        //B 打了断点,会卡在方法中间
        executor.execute(new SimpleDateFormatTest("B", "2013-02-13", false));
        executor.shutdown();

    }
}
