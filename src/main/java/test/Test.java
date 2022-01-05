package test;


import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import utils.ListUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Test {

    private static String DATE_10_30 = "2021-09-22";//2021-10-30 00:00:00
    private static String DATE_10_31 = "2021-09-23";//2021-10-31 00:00:00
    private static String DATE_11_01 = "2021-09-24";//2021-11-01 00:00:00
    private static String h5PagePrefix = "panipuri://com.funnypuri.client/app/web?url=";
    private static String zpointPage ="http://sandbox-h5.zilivideo.com/h5/zPoints/result";
    private static String videoDetailPrefix = "panipuri://com.funnypuri.client/app/moments/detail?type=4&newsId=%s";
    private static String zpointsIdentity = "&zpointType=zpoints";
    /**
     * top 用户的截取阈值
     */
    private static final int THRESHOLD_TOP = 2;

    public void test() {
        List<UserMedalDO> list = new ArrayList<UserMedalDO>();
        list.add(new UserMedalDO(1, 1));
        list.add(new UserMedalDO(1, 2));
        list.add(new UserMedalDO(1, 3));

        list.add(new UserMedalDO(2, 1));
        list.add(new UserMedalDO(2, 2));

        list.add(new UserMedalDO(3, 1));

        //Optional<UserMedalDO> max = list.stream().max(Comparator.comparing(UserMedalDO::getLevel));
        //System.out.println(max.get().getLevel());

        //返回该用户SABC各个等级的勋章总数
//        Map<Integer, List<UserMedalDO>> collect = list.stream().collect(Collectors.groupingBy(t -> t.getLevel()));
//        List<UserMedalLevelDO> level = new ArrayList<>();
//        collect.forEach((key,value) -> {
//            level.add(new UserMedalLevelDO(key, value.size()));
//        });
//        System.out.println();

        //首先筛选出该序列的详情
        int seriesNo = 2;
        List<UserMedalDO> collect = list.stream().filter(item -> item.getSeriesNo() == seriesNo).collect(Collectors.toList());
        System.out.println();
    }

    public void test2() {
        UserMedalSeriesEnum seriesEnum = UserMedalSeriesEnum.SERIES_POST;
        UserMedalLevelRnum level = null;
        int curCnt = 1;
        List<UserMedalRulesEnum> serieLevelCnt = UserMedalRulesEnum.getSerieLevelCnt(seriesEnum);
        for (UserMedalRulesEnum userMedalRulesEnum : serieLevelCnt) {
            if (curCnt >= userMedalRulesEnum.getCnt()) {
                level = userMedalRulesEnum.getLevel();
                break;
            }
        }

        System.out.println(level);
    }


    public void test3() {
        List<UserMedalSerieMaxLevelDO> list = new ArrayList<>();

        list.add(new UserMedalSerieMaxLevelDO(1, 4));
        list.add(new UserMedalSerieMaxLevelDO(2, 2));
        list.add(new UserMedalSerieMaxLevelDO(3, 1));


        Map<Integer, Integer> serieMaxLevelMap = new HashMap<>();
        list.forEach(item -> {
            //serieMaxLevelMap.put(item.getSeriesNo(), item.getLevel());
        });

        System.out.println();
    }

    public void test4() {
        Date time = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(time);
        System.out.println(dateString);
    }

    public void test5() {
        List<UserMedalDO> userMedalList = new ArrayList<>();
        int seriesNo = 1;
        //userMedalList= userMedalList.stream().filter(item -> item.getSeriesNo() == seriesNo).collect(Collectors.toList());
        //userMedalList.stream().filter(item -> item.getVisibleStatus()!=1).collect(Collectors.toList());
        //Map<Integer, List<UserMedalDO>> userMedalMap = userMedalList.stream().collect(Collectors.groupingBy(t -> t.getLevel()));
        // boolean b =  userMedalList.stream().filter(item -> item.getSeriesNo() == UserMedalSeriesEnum.SERIES_POST.getSeriesNo()).collect(Collectors.toList()).size() > 0;
        // int i = userMedalList.stream().max(Comparator.comparing(UserMedalDO::getLevel)).get().getLevel().intValue();
        Optional<UserMedalDO> max = userMedalList.stream().max(Comparator.comparing(UserMedalDO::getLevel));
        int i = max.isPresent() ? max.get().getLevel() : 0;
        System.out.println();
    }


    public void test6() {
        //        获取Calendar对象，用于自定时时间
        Calendar c = Calendar.getInstance();
//        绑定当前时间的年月日，年就是本年，月是本月，日是下一日（如果是-1的话，就是昨天喽）
        //c.add(Calendar.YEAR,0);
        //c.add(Calendar.MONTH,0);
        //c.add(Calendar.DAY_OF_MONTH,0);
//        设置当前时间的时分秒，时就是凌晨4点，分是0分，秒是0秒
        c.set(Calendar.HOUR_OF_DAY, 3); //HOUR_OF_DAY是24小时制，HOUR是12小时制
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        long ts = c.getTimeInMillis();
        Date date = new Date(ts);
        System.out.println();
    }

    public void test7() {
        //        Date endTime = new Date();
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(endTime);
//        rightNow.add(Calendar.DAY_OF_MONTH, -1);
//        Date startTime = rightNow.getTime();
//        System.out.println();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Collections.shuffle(list);
        System.out.println();
    }

    public void test8() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayZero = calendar.getTime();

        Calendar leftNow = Calendar.getInstance();
        leftNow.setTime(todayZero);
        leftNow.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterydayNow = leftNow.getTime();
        System.out.println();
    }

    public static void test9() {
        String s = "\"1000002\",\"Kabul\",\"Kabul,Kabul,Afghanistan\",\"9075393\",\"AF\",\"City\",Active";
        String[] split = s.split("\"");

        StringTokenizer token = new StringTokenizer(s, "\"");
        while (token.hasMoreElements()) {
            System.out.print(token.nextToken() + "  ");
        }

        System.out.println();

    }

    public static void test10() {
        int a = 6;
        int b = 7;
        int c = 6 & 7;
        System.out.println();
    }


    public void test12() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String format = sdf.format(now);
        System.out.println();
    }

    public void test13() {
//        List<Object> objects = Arrays.asList(new String[]{"d1", "d2"});
//        String s = "d_1,d_2,d_3";
//        List<String> userTagList = Arrays.asList(s.split(","));

        List<TaskUserVideoDTO> topUserList = new ArrayList<>();
        topUserList.add(new TaskUserVideoDTO("wx", 1001));
        topUserList.add(new TaskUserVideoDTO("mc", 10000));
        topUserList.add(new TaskUserVideoDTO("zjf", 900));

        topUserList = topUserList.stream().sorted(Comparator.comparing(TaskUserVideoDTO::getTotalExposureCount).reversed()).limit(2).collect(Collectors.toList());
        System.out.println();
    }

    public void test14() {
        Queue<TaskUserVideoDTO> topUserList = new PriorityQueue<>(THRESHOLD_TOP);
        topUserList.add(new TaskUserVideoDTO("wx", 900));
        topUserList.add(new TaskUserVideoDTO("mc", 1000));

        //if (topUserList.peek())
        //加一个，移除最小值
        topUserList.add(new TaskUserVideoDTO("zjf", 800));
        topUserList.remove();

        topUserList.add(new TaskUserVideoDTO("hy", 801));
        topUserList.remove();

        topUserList.add(new TaskUserVideoDTO("wx2", 901));
        topUserList.remove();

        topUserList.add(new TaskUserVideoDTO("wx3", 10001));
        topUserList.remove();

        List<String> topList = new ArrayList<>();
        Iterator<TaskUserVideoDTO> iterator = topUserList.iterator();
        while (iterator.hasNext()) {
            topList.add(iterator.next().getUserId());
        }
    }

    public static void test15() {
        List<UserRankingDetailVO> userList = new ArrayList<>();

        userList.stream().collect(Collectors.toMap(UserRankingDetailVO::getUserId, UserRankingDetailVO -> UserRankingDetailVO));

        List<Long> filterIdList = new ArrayList<>();


        filterIdList.add(256L);
        UserRankingDetailVO userRankingDetailVO = new UserRankingDetailVO();
        userRankingDetailVO.setUserId("wx");
        userRankingDetailVO.setRanking(26L);
        userList.add(userRankingDetailVO);

        userRankingDetailVO = new UserRankingDetailVO();
        userRankingDetailVO.setUserId("hy");
        userRankingDetailVO.setRanking(256L);
        userList.add(userRankingDetailVO);


        //Map<String, UserRankingDetailVO> collect = userList.stream().collect(Collectors.toMap(UserRankingDetailVO::getUserId, UserRankingDetailVO -> UserRankingDetailVO, (key1, key2) -> key2));
        //ArrayList<UserRankingDetailVO> userRankingDetailVOS = new ArrayList<>(collect.values());

//        for (UserRankingDetailVO userRankingDetailVO1: userList) {
//            if (filterIdList.contains(userRankingDetailVO1.getRanking())) {
//                userList.remove(userRankingDetailVO1);
//            }
//        }

        userList = userList.stream().filter(t -> !filterIdList.contains(t.getRanking())).collect(Collectors.toList());
        Set<String> userIdSet = ListUtils.getColumnToSet(userList, "userId");

        ArrayList<String> strings = new ArrayList<>(userIdSet);

        System.out.println();
    }

    public void test16(String s1) {
        s1 = "cd";
        System.out.println("s1 = " + s1);
    }

    public static void urlEncoder() throws UnsupportedEncodingException {
        int category = 39;
        int count = 1;
        String h1 = "http://sandbox-h5.zilivideo.com/h5/zPoints/result?category=%s&count=%s";
        String format = String.format(h1, category, count);
        String encode = URLEncoder.encode(format, "UTF-8");
        System.out.println(encode);
    }

    public static void test18() {
        //标签方法
        retry:
        System.out.println("retry");
//        for(int i =0 ;i < 10; i++) {
//            System.out.println("retry foreach : " + i);
//            if (i==5) {
//                System.out.println(" i == 5, continue");
//                continue retry;
//            }
//
//            if (i>=5) {
//                break retry;
//            }
//        }
        System.out.println("retry end");
    }

    /**
     * executorService 出现RejectedExecutionException时，会抛出异常，
     * 1。在任务提交的外面捕获异常，就可以结束countDownLatch.await();避免主线城阻塞
     * 2。executorService抛出RejectedExecutionException异常后，仍可以再次添加任务，执行任务，直至再次抛出异常
     *
     * @throws InterruptedException
     */
    public static void test17() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3, true),
                new ThreadFactoryBuilder().setNameFormat("pointsVideoQueryThread-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        //AtomicInteger count = new AtomicInteger();
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        try {
            for (int i = 0; i < 500; i++) {
                executorService.submit(() -> {
                    //count.getAndIncrement();
                    //System.out.println("sumbit task 【" + count.get() + "】");
                    try {
                        System.out.println("task ：" + countDownLatch.getCount());
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        countDownLatch.countDown();
                    }

                });
            }

            countDownLatch.await();
            System.out.println("========================== for  end ==========================");
        } catch (Exception e) {
            System.out.println(e);
        }

        //executorService.shutdown();
        System.out.println("############################## function end 1 ##############################");
        try {
            for (int i = 0; i < 500; i++) {
                executorService.submit(() -> {
                    //count.getAndIncrement();
                    //System.out.println("sumbit task 【" + count.get() + "】");
                    try {
                        System.out.println("task2 ：" + countDownLatch.getCount());
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        countDownLatch.countDown();
                    }

                });
            }

            countDownLatch.await();
            System.out.println("========================== for  end2 ==========================");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("############################## function end 2 ##############################");


        try {
            for (int i = 0; i < 500; i++) {
                executorService.submit(() -> {
                    //count.getAndIncrement();
                    //System.out.println("sumbit task 【" + count.get() + "】");
                    try {
                        System.out.println("task3 ：" + countDownLatch.getCount());
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        countDownLatch.countDown();
                    }

                });
            }

            countDownLatch.await();
            System.out.println("========================== for  end3 ==========================");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("############################## function end 3 ##############################");
    }

    public static void test19(int n) {
        switch (n) {
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
            case 4:
                System.out.println("4");
                break;
            default:
                break;
        }
    }


    public static void main(String[] args) throws ParseException, UnsupportedEncodingException, InterruptedException {
        Test test = new Test();
        test19(5);
        int low = 0;
        int high = 100;
        int CQ_STORE_UNIT_SIZE = 20;
        int m = (low + high) / (2 * CQ_STORE_UNIT_SIZE) * CQ_STORE_UNIT_SIZE;
        System.out.println("m = " + m);
    }


}
