package mybatis.luban.mapper;

import mybatis.luban.model.CommentDO;

import java.util.List;

public interface CommentMapper {
    List<CommentDO> selectCommentsByBlogId(long blogId);
}
