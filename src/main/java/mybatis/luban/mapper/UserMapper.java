package mybatis.luban.mapper;

import mybatis.luban.model.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    UserDO selectUserById(long id);

    UserDO selectUserById2(long id);

    int setName(@Param("id")Integer id, @Param("name") String name);

    int deleteUserById(Integer id);

    int addUser(UserDO user);

    UserDO selectUserByNameOrAge(String name, int age);

    UserDO selectUserByNameOrAge2(@Param("name") String name, int age);

    UserDO selectUserByUser(@Param("name") String name, UserDO user);

    List<UserDO> selectBatchUser();
}
