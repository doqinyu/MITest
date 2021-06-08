package test;

import java.util.*;
import java.util.stream.Collectors;

public enum UserMedalRulesEnum {
    SERIES_POST_LEVEL_C(UserMedalSeriesEnum.SERIES_POST,UserMedalLevelRnum.USER_MEDAL_LEVEL_C,1L, 1,"发布序列C等级阈值"),
    SERIES_POST_LEVEL_B(UserMedalSeriesEnum.SERIES_POST,UserMedalLevelRnum.USER_MEDAL_LEVEL_B,10L, 2,"发布序列B等级阈值"),
    SERIES_POST_LEVEL_A(UserMedalSeriesEnum.SERIES_POST,UserMedalLevelRnum.USER_MEDAL_LEVEL_A,100L, 3,"发布序列A等级阈值"),
    SERIES_POST_LEVEL_S(UserMedalSeriesEnum.SERIES_POST,UserMedalLevelRnum.USER_MEDAL_LEVEL_S,1000L, 4,"发布序列S等级阈值"),

    SERIES_SINGLE_VIEW_LEVEL_C(UserMedalSeriesEnum.SERIES_SINGLE_VIEW,UserMedalLevelRnum.USER_MEDAL_LEVEL_C,10000L, 2,"发布序列C等级阈值"), // 1w
    SERIES_SINGLE_VIEW_LEVEL_B(UserMedalSeriesEnum.SERIES_SINGLE_VIEW,UserMedalLevelRnum.USER_MEDAL_LEVEL_B,100000L, 4,"单视频曝光序列B等级阈值"), // 10w
    SERIES_SINGLE_VIEW_LEVEL_A(UserMedalSeriesEnum.SERIES_SINGLE_VIEW,UserMedalLevelRnum.USER_MEDAL_LEVEL_A,1000000L, 6,"单视频曝光序列A等级阈值"), //100w
    SERIES_SINGLE_VIEW_LEVEL_S(UserMedalSeriesEnum.SERIES_SINGLE_VIEW,UserMedalLevelRnum.USER_MEDAL_LEVEL_S,10000000L, 8,"单视频曝光序列S等级阈值"); //1000w

    private UserMedalSeriesEnum series; //系列
    private UserMedalLevelRnum level; //系列等级
    private long cnt; //阈值
    private int weight; //权重（计算排名时使用）
    private String desc; //描述

    private static Map<String, UserMedalRulesEnum> userMedalRulesEnumMap;
    private static int weightTotalCnt; //加权勋章总数

    static {
        userMedalRulesEnumMap = new HashMap<>();
        for (UserMedalRulesEnum userMedalRulesEnum : UserMedalRulesEnum.values()) {
            String key = getUserMedalRulesEnumKey(userMedalRulesEnum.getSeries().getSeriesNo(), userMedalRulesEnum.getLevel().getLevel());
            userMedalRulesEnumMap.put(key, userMedalRulesEnum);
            weightTotalCnt += userMedalRulesEnum.getWeight();
        }
    }


    UserMedalRulesEnum(UserMedalSeriesEnum series, UserMedalLevelRnum level, long cnt, int weight, String desc) {
        this.series = series;
        this.level = level;
        this.cnt = cnt;
        this.weight = weight;
        this.desc = desc;
    }

    public UserMedalSeriesEnum getSeries() {
        return series;
    }

    public UserMedalLevelRnum getLevel() {
        return level;
    }

    public long getCnt() {
        return cnt;
    }

    public int getWeight() {
        return weight;
    }

    //获取series系列的所有规则,并按阈值cnt大小降序返回
    public static List<UserMedalRulesEnum> getSerieLevelCnt(UserMedalSeriesEnum series) {
        List<UserMedalRulesEnum> userMedalRulesEnumList = new ArrayList<UserMedalRulesEnum>();
        for (UserMedalRulesEnum userMedalRulesEnum : UserMedalRulesEnum.values()) {
            if (userMedalRulesEnum.getSeries() == series) {
                userMedalRulesEnumList.add(userMedalRulesEnum);
            }
        }
        return userMedalRulesEnumList.stream().sorted(Comparator.comparing(UserMedalRulesEnum::getCnt).reversed()).collect(Collectors.toList());
    }

    public static String getUserMedalRulesEnumKey(int serieNo, int level){
        return serieNo + ":" + level;
    }

    //根据序列号和等级获取规则（有权重）
    public static UserMedalRulesEnum getUserMedalRulesEnum(int serieNo, int level) {
        String key = getUserMedalRulesEnumKey(serieNo, level);
        return userMedalRulesEnumMap.get(key);
    }

    public static int getWeightTotalCnt() {
        return weightTotalCnt;
    }
}
