package test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsSpecialCoeDTO {
    /**
     * 理由。 1:合拍 2:道具 3:领拍 4:活动
     */
    Integer type;
    /**
     * 当前Treand对应的特殊系数
     */
    Double coe;

    /**
     *
     * @param matchSource 匹配来源 1:demo  2:tag
     * @return
     */

}
