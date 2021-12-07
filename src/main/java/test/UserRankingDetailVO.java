package test;

public class UserRankingDetailVO extends UserRankingDetail {
    private int followStatus; //关注状态   0：未关注  1：已关注, -1：自己


    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }
}
