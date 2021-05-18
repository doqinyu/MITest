package mybatis.luban.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDO implements Serializable {
    private long id;
    private String name;
    private int age;
}
