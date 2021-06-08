package test;

import lombok.AllArgsConstructor;
import lombok.Data;

//查询用户勋章每个序列的最高等级对应的DO
@AllArgsConstructor
@Data
public class UserMedalSerieMaxLevelDO {
    private Integer level; //等级 1:C 2:B 3:A 4:S
    private Integer weightMedalCnt; //该等级对应的勋章数

}
