package mybatis.luban.Test;

import mybatis.luban.model.BlogDO;
import mybatis.luban.model.CommentDO;
import mybatis.luban.model.UserDO;

import java.util.ArrayList;

public class Mock {

    public static BlogDO mockBlogDO() {
        BlogDO blogDO = new BlogDO();

        ArrayList list = new ArrayList<String>();
        list.add(mockCommentDO());
        blogDO.setComments(list);
        return blogDO;
    }

    public static CommentDO mockCommentDO() {
        CommentDO commentDO = new CommentDO();
        commentDO.setBlogId(1);
        commentDO.setContent("mock comment");
        commentDO.setUser(mockUserDO());
        return commentDO;
    }

    public static UserDO mockUserDO () {
        UserDO userDO = new UserDO();
        userDO.setName("mock user name");
        return userDO;
    }
}
