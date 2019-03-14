package com.xusenme.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xusenme.dao.MyFileDao;
import com.xusenme.model.MyFile;
import com.xusenme.service.FileService;
import com.xusenme.utils.FastDFSClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;


@Service
@Transactional
public class FileServiceImpl implements FileService {


    @Autowired
    private MyFileDao myFileDao;

    @Override
    public Object upload(String userId, Map<String, Object> paramMap, HttpServletRequest request) {
//        List<String> resultList = new ArrayList<>();
//        JSONObject jso = JSONObject.parseObject((String) paramMap.get("md5"));
//        Map<String, Object> md5Map = JSONObject.toJavaObject(jso, Map.class);

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
            String extName = file.getOriginalFilename().split("\\.")[1];
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
                myFileDao.saveUserFile(UUID.randomUUID().toString(), myFile.getId(), userId, dir);
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", "true");
        return result;
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
}
