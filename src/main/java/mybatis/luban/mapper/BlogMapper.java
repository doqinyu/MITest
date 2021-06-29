package mybatis.luban.mapper;

import mybatis.luban.model.BlogMap;
import mybatis.luban.model.BlogMap3;

public interface BlogMapper {

    //嵌套查询
    BlogMap selectBlogById(long id);

    //联合查询
    BlogMap selectBlogById2(long id);

    BlogMap3 selectBlogById3(long id);

    //循环依赖
    BlogMap selectBlogById4(long id);
}
