package mybatis.luban.model;

import lombok.Data;

@Data
public class CommentDO {
    private long id;
    //private long blogId;
    private BlogMap blog;
    //private long userId;
    private String content;
    //private UserDO user;

}
