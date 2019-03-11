package com.xusenme.dao;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User getNameById(User user);

    User getUserByEmail(UserVo userVo);

    int createUser(User user);

    int active(String id);

    User getUserByEmailAndPassword(UserVo userVo);
}
