package mybatis.luban.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlogMap implements Serializable {
    private long id;
    //private long userId;
    private String title;
    //private long commentId;
    private List<CommentDO> comments;
    //private String comment;
    //private UserDO author;

}
