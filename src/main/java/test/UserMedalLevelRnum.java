package test;

public enum UserMedalLevelRnum {

    USER_MEDAL_LEVEL_C(1, "用户勋章体系-C级"),
    USER_MEDAL_LEVEL_B(2, "用户勋章体系-B级"),
    USER_MEDAL_LEVEL_A(3, "用户勋章体系-A级"),
    USER_MEDAL_LEVEL_S(4, "用户勋章体系-S级");
    private int level;
    private String desc;



    UserMedalLevelRnum(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public int getLevel() {
        return level;
    }

}
