package test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static String DATE_10_30 = "2021-09-22";//2021-10-30 00:00:00
    private static String DATE_10_31 = "2021-09-23";//2021-10-31 00:00:00
    private static String DATE_11_01 = "2021-09-24";//2021-11-01 00:00:00
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

    public static void main(String[] args) throws ParseException {
        Test test = new Test();
        //test.test12();
        //test.test13();
//        long rocketTeamExposureCount = 10L;
//        long sutliBombTeamExposureCount = 3L;
//        int rate = (int) ((rocketTeamExposureCount / sutliBombTeamExposureCount) * 10000);
//        System.out.println();

        Queue<TaskUserVideoDTO> topUserList = new PriorityQueue<>(THRESHOLD_TOP);
        topUserList.add(new TaskUserVideoDTO("wx", 900));
        topUserList.add(new TaskUserVideoDTO("mc", 1000));

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


        System.out.println();
    }

}
