package mybatis.luban.mapper;

import mybatis.luban.model.BlogMap;

public interface BlogMapper {

    BlogMap selectBlogById(long id);
}
