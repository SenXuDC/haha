package com.xusenme.service;

import com.xusenme.controller.vo.ShareVo;
import com.xusenme.model.MyFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface FileService {

    Object upload(String userId, Map<String, Object> paramMap, HttpServletRequest request);

    List<MyFile> listDir(String userId, String dir);

    Object createFolder(String userId, String folder,String dir);

    void download(String id, HttpServletResponse response);

    Object deleteFile(String id);

    List<MyFile> listRecycle(String userId);

    Object recoveryFile(String id);

    Object shareFile(ShareVo shareVo);

    Object shareGet(ShareVo shareVo);
}
