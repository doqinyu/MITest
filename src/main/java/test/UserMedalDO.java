package test;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//表user_medel对应的DO
@Data
@NoArgsConstructor
public class UserMedalDO {
    Long id;
    String userId;
    Integer seriesNo;
    Integer level;
    Date createTime;
    Integer visibleStatus; //是否仅自己可见 0：所有人可见 1：仅自己可见

    public UserMedalDO(Integer seriesNo, Integer level) {
        this.seriesNo = seriesNo;
        this.level = level;
        createTime = new Date();
    }
}
