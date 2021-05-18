package mybatis.luban.mapper;

import mybatis.luban.model.VideoDO;

public interface VideoMapper {
    VideoDO selectVideoByVideoId(String videoId);
}
