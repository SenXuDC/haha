package com.xusenme.dao;

import com.xusenme.model.MyFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MyFileDao {

    MyFile getMyFileByMd5(String md5);

    int saveMyFile(MyFile myFile);

    int saveUserFile(String id, String userId, String file_id, String dir);

    List<MyFile> getAllMyFile();

    int updateMyFile(MyFile myFile);
}
