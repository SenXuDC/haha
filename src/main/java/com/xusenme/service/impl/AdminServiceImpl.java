package com.xusenme.service.impl;

import com.xusenme.controller.vo.UpdateUserVo;
import com.xusenme.dao.MyFileDao;
import com.xusenme.dao.UserDao;
import com.xusenme.model.MyFile;
import com.xusenme.model.User;
import com.xusenme.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MyFileDao myFileDao;

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public List<MyFile> listMyFile() {
        return myFileDao.getAllMyFile();
    }

    @Override
    public Object updateUser(UpdateUserVo updateUserVo) {
        return userDao.updateUser(updateUserVo);
    }

    @Override
    public Object updateMyFile(MyFile myFile) {
        return myFileDao.updateMyFile(myFile);
    }
}
