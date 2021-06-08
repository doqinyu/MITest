package test;

import lombok.AllArgsConstructor;
import lombok.Data;

//查询用户勋章系列等级对应的DO
@AllArgsConstructor
@Data
public class UserMedalLevelDO {
    private Integer level; //等级 1:C 2:B 3:A 4:S
    private Integer count; //对应等级的勋章总数
}
