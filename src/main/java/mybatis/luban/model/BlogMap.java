package mybatis.luban.model;

import lombok.Data;

import java.util.List;

@Data
public class BlogMap {
    private long id;
    //private long userId;
    private String title;
    //private long commentId;
    private List<CommentDO> comments;
    private UserDO author;

}
