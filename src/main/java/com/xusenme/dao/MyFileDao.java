package com.xusenme.dao;

import com.xusenme.controller.vo.ShareVo;
import com.xusenme.model.MyFile;
import com.xusenme.model.Share;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MyFileDao {

    MyFile getMyFileByMd5(String md5);

    int saveMyFile(MyFile myFile);

    int saveUserFile(String id, String userId, String file_id, String dir);

    List<MyFile> getAllMyFile();

    int updateMyFile(MyFile myFile);

    List<MyFile> listDir(String userId, String dir, String findFilename);

    int createFolder(String id, String folder);

    MyFile getMyFileById(String id);

    int deleteFile(String id);

    List<MyFile> listRecycle(String userId);

    int recoveryFile(String id);

    int publicShare(ShareVo shareVo);

    int passwordShare(ShareVo shareVo);

    int privateShare(ShareVo shareVo);

    List<Share> getShareById(ShareVo shareVo);
}
