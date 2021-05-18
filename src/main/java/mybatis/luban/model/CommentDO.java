package mybatis.luban.model;

import lombok.Data;

@Data
public class CommentDO {
    private long id;
    private long blogId;
    private long userId;
    private String content;
}
