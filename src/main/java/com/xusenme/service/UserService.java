package com.xusenme.service;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;

import java.util.Map;

public interface UserService {

    User getUserById(User user);

    Map<String,String> createUser(UserVo userVo);

    String active(String id);

    Map<String, String> login(UserVo userVo);
}
