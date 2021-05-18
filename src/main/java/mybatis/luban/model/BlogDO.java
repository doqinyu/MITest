package mybatis.luban.model;

import lombok.Data;

@Data
public class BlogDO {

    private long id;
    private long userId;
    private String title;
    private long commentId;
    private UserDO user;

}
