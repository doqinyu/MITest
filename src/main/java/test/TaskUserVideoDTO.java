package test;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskUserVideoDTO implements Comparable<TaskUserVideoDTO>{
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户带diwali tag有效视频的总曝光量
     */
    private long totalExposureCount;


    //按照曝光量升序排序
    @Override
    public int compareTo(TaskUserVideoDTO o) {
        return (int)(this.totalExposureCount - o.getTotalExposureCount());
    }
}
