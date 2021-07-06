package mybatis.luban.model;

import lombok.Data;

@Data
public class CommentMap {
    private long id;
    private long blogId;
    //private long userId;
    private String content;
    //private UserDO user;
    //private BlogMap3 blog;
}
