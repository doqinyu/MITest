package mybatis.luban.mapper;

import mybatis.luban.model.BlogMap;
import mybatis.luban.model.BlogMap3;

public interface BlogMapper {

    BlogMap selectBlogById(long id);

    BlogMap selectBlogById2(long id);

    BlogMap3 selectBlogById3(long id);
}
