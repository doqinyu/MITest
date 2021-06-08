package test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public void test() {
        List<UserMedalDO> list = new ArrayList<UserMedalDO>();
        list.add(new UserMedalDO(1,1));
        list.add(new UserMedalDO(1,2));
        list.add(new UserMedalDO(1,3));

        list.add(new UserMedalDO(2,1));
        list.add(new UserMedalDO(2,2));

        list.add(new UserMedalDO(3,1));

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
        for (UserMedalRulesEnum userMedalRulesEnum: serieLevelCnt) {
            if (curCnt >= userMedalRulesEnum.getCnt()) {
                level= userMedalRulesEnum.getLevel();
                break;
            }
        }

        System.out.println(level);
    }


    public void test3() {
        List<UserMedalSerieMaxLevelDO> list = new ArrayList<>();

        list.add(new UserMedalSerieMaxLevelDO(1,4));
        list.add(new UserMedalSerieMaxLevelDO(2,2));
        list.add(new UserMedalSerieMaxLevelDO(3,1));


        Map<Integer, Integer>  serieMaxLevelMap = new HashMap<>();
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
        Map<Integer, List<UserMedalDO>> userMedalMap = userMedalList.stream().collect(Collectors.groupingBy(t -> t.getLevel()));

       // int i = userMedalList.stream().max(Comparator.comparing(UserMedalDO::getLevel)).get().getLevel().intValue();
        System.out.println();
    }



    public static void main(String[] args) {
        Test test = new Test();
        //test.test();
        //List<String> list = Arrays.asList("wx");
        //System.out.println(list.size());
        //test.test2();
        //test.test3();
        //test.test4();
        test.test5();
    }
}
