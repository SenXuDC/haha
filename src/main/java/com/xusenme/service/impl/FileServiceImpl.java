package com.xusenme.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xusenme.controller.vo.ShareVo;
import com.xusenme.dao.MyFileDao;
import com.xusenme.model.MyFile;
import com.xusenme.model.Share;
import com.xusenme.service.FileService;
import com.xusenme.utils.FastDFSClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;


@Service
@Transactional
public class FileServiceImpl implements FileService {


    @Autowired
    private MyFileDao myFileDao;

    @Value("${fastdfs.url}")
    private String fastdfsUrl;

    @Override
    public Object upload(String userId, Map<String, Object> paramMap, HttpServletRequest request) {
        String dir = (String) paramMap.get("dir");
        if (dir == null) {
            dir = "/";
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            MyFile myFile = new MyFile();
            file = files.get(i);
            myFile.setId(UUID.randomUUID().toString());
            myFile.setFilename(file.getOriginalFilename());
            myFile.setSize((int) file.getSize());
            String extName = null;
            try {
                extName = file.getOriginalFilename().split("\\.")[1];
            } catch (Exception e) {

            }

            String fileName = file.getOriginalFilename().split("\\.")[0];

            // 将文件上传到fastdfs或者已在fastdfs中存在
            if (!file.isEmpty()) {
                String md5 = getMd5(file);
                myFile.setMd5(md5);
                String fastDfdPath = null;
                MyFile beforeFile = myFileDao.getMyFileByMd5(md5);
                if (beforeFile == null) {
                    try {
                        byte[] bytes = file.getBytes();
                        fastDfdPath = FastDFSClientUtils.upload(bytes, extName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // 在fastdfs中已经存在了相同的文件,不用再次放到fastdfs上
                    fastDfdPath = beforeFile.getFastdfsPath();
                }
                myFile.setFastdfsPath(fastDfdPath);
                myFileDao.saveMyFile(myFile);
                myFileDao.saveUserFile(UUID.randomUUID().toString(), userId, myFile.getId(), dir);
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", "true");
        return result;
    }

    @Override
    public List<MyFile> listDir(String userId, String dir) {
        List<MyFile> myFileList = myFileDao.listDir(userId, dir);
        for (MyFile myFile : myFileList) {
            if (myFile.getActive()) {
                myFile.setFastdfsPath("http://" + fastdfsUrl + "/" + myFile.getFastdfsPath());
            } else {
                myFile.setFastdfsPath("");
            }
        }
        return myFileList;
    }

    @Override
    public List<MyFile> listRecycle(String userId) {
        return myFileDao.listRecycle(userId);
    }

    @Override
    public Object recoveryFile(String id) {
        return myFileDao.recoveryFile(id);
    }

    @Override
    public Object createFolder(String userId, String folder, String dir) {
        String folderId = UUID.randomUUID().toString();
        myFileDao.createFolder(folderId, folder);
        return myFileDao.saveUserFile(UUID.randomUUID().toString(), userId, folderId, dir);

    }

    @Override
    public void download(String id, HttpServletResponse response) {
        MyFile myFile = myFileDao.getMyFileById(id);
        try {
            response.getOutputStream().write(FastDFSClientUtils.download("group1", myFile.getFastdfsPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Object deleteFile(String id) {
        return myFileDao.deleteFile(id);
    }

    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     */
    public String getMd5(MultipartFile file) {

        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object shareFile(ShareVo shareVo) {
        String shareId = UUID.randomUUID().toString();
        shareVo.setShareId(shareId);
        Map<String, String> map = new HashMap<>();
        map.put("shareId", shareId);
        switch (shareVo.getShareType()) {
            case "public":
                myFileDao.publicShare(shareVo);
                break;
            case "password":
                String password = UUID.randomUUID().toString().substring(0, 4);
                shareVo.setPassword(password);
                myFileDao.passwordShare(shareVo);
                map.put("password", password);
                break;
            case "private":
                myFileDao.privateShare(shareVo);
                break;
        }
        return map;
    }

    @Override
    public Object shareGet(ShareVo shareVo) {
        List<Share> shares = myFileDao.getShareById(shareVo);
        if (shares.size() <= 0) {
            return shares;
        }
        switch (shares.get(0).getShareType()) {
            case "public":
                return shares;
            case "password":
                if (shareVo.getPassword()!=null && shareVo.getPassword().equals(shares.get(0).getPassword())) {
                    return shares;
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("result","false");
                    map.put("message","密码错误");
                    return map;
                }
            case "private":
                if (shareVo.getEmail().equals(shares.get(0).getEmail())) {
                    return shares;
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("result","false");
                    map.put("message","该分享是私有分享，请切换正确的账号");
                    return map;
                }
        }
        return shares;
    }
}
