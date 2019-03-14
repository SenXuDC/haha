package com.xusenme.service;

import com.xusenme.controller.vo.UpdateUserVo;
import com.xusenme.model.MyFile;
import com.xusenme.model.User;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<User> listUser();

    List<MyFile> listMyFile();

    Object updateUser(UpdateUserVo updateUserVo);

    Object updateMyFile(MyFile myFile);
}
