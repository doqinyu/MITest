package mybatis.luban.mapper;

import mybatis.luban.model.CommentDO;
import mybatis.luban.model.CommentMap;

import java.util.List;

public interface CommentMapper {
    CommentMap selectCommentsByBlogId(int blogId);
    List<CommentDO> selectCommentsByBlogId2(int blogId);
}
