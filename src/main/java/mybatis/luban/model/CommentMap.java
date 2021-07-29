package mybatis.luban.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentMap implements Serializable {
    private long id;
    private long blogId;
    //private long userId;
    private String content;
    //private UserDO user;
    //private BlogMap3 blog;
}
