package test;


public class UserRankingDetail {
    private Long ranking; //排名
    private String icon; //	用户头像
    private String userId; //用户id
    private String nickName; //用户名称
    private long fansCount; //粉丝总量
    private long addedFans; //粉丝数增量
    /**
     * 话题相关的挂件
     */
    private String topicWidgetUrl = "";
    /**
     * 话题的deeplink
     */
    private String topicLink = "";

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }

    public long getAddedFans() {
        return addedFans;
    }

    public void setAddedFans(long addedFans) {
        this.addedFans = addedFans;
    }

    public String getTopicWidgetUrl() {
        return topicWidgetUrl;
    }

    public void setTopicWidgetUrl(String topicWidgetUrl) {
        this.topicWidgetUrl = topicWidgetUrl;
    }

    public String getTopicLink() {
        return topicLink;
    }

    public void setTopicLink(String topicLink) {
        this.topicLink = topicLink;
    }
}
