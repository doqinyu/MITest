package mybatis.luban.Test;

import mybatis.luban.model.BlogDO;
import mybatis.luban.model.CommentDO;
import mybatis.luban.model.UserDO;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    public static BlogDO mockBlogDO() {
        BlogDO blogDO = new BlogDO();
        blogDO.setComments(mockCommentDOList());
        return blogDO;
    }

    public static CommentDO mockCommentDO() {
        CommentDO commentDO = new CommentDO();
        //commentDO.setBlogId(1);
//        commentDO.setContent("mock comment");
//        commentDO.setUser(mockUserDO());
        return commentDO;
    }

    public static List<CommentDO> mockCommentDOList() {
        ArrayList list = new ArrayList<String>();
        CommentDO commentDO = new CommentDO();
        //commentDO.setBlogId(1);
        commentDO.setContent("mock comment");
//        commentDO.setUser(mockUserDO());
        list.add(commentDO);
        commentDO = new CommentDO();
        //commentDO.setBlogId(1);
        commentDO.setContent("mock comment222");
//        commentDO.setUser(mockUserDO());
        list.add(commentDO);
        return list;
    }

    public static UserDO mockUserDO () {
        UserDO userDO = new UserDO();
        userDO.setName("mock user name");
        return userDO;
    }
}
