package test;

import java.util.HashMap;
import java.util.Map;

//用户勋章体系系列枚举
public enum UserMedalSeriesEnum {
    SERIES_POST(1, "创作达人", "发布序列"),
    SERIES_SINGLE_VIEW(2, "爆款大师", "单视频曝光序列"),
    SERIES_VIEW(3, "流量之王", "总曝光序列");



    private static Map<Integer, UserMedalSeriesEnum> userMedalSeriesMap;
    private int seriesNo; //系列序列号
    private String title;//系列文案
    private String desc; //描述

    static {
        userMedalSeriesMap = new HashMap<>();
        for (UserMedalSeriesEnum userMedalSeriesEnum : UserMedalSeriesEnum.values()) {
            userMedalSeriesMap.put(userMedalSeriesEnum.getSeriesNo(), userMedalSeriesEnum);
        }
    }

    UserMedalSeriesEnum(int seriesNo, String title, String desc) {
        this.seriesNo = seriesNo;
        this.title = title;
        this.desc = desc;
    }

    public int getSeriesNo() {
        return seriesNo;
    }

    public String getTitle() {
        return title;
    }
}
