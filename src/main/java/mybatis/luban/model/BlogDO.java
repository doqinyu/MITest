package mybatis.luban.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class BlogDO {

    private long id;
    private long userId;
    private String title;
    private List<CommentDO> comments;
    private UserDO author;
    private HashMap labels;

}
