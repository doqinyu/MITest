package test;

import lombok.Data;

@Data
public class EXP {
    /**
     * 区间曝光下限（包括）
     */
    private long lowExp;
    /**
     * 区间曝光上限（包括）
     */
    private long highExp;
    /**
     * 每单位曝光成本
     */
    private float cost;
}
