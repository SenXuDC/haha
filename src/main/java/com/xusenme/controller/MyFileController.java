package com.xusenme.controller;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;
import com.xusenme.service.FileService;
import com.xusenme.utils.FastDFSClientUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class MyFileController {

    @Autowired
    private FileService fileService;

//    @ApiOperation(value = "上传文件", notes = "")
//    @RequestMapping(value = "/uploadSingle/{userId}", method = RequestMethod.POST)
//    @ResponseBody
//    public String createUser(@PathVariable("userId") String userId, @RequestParam("file") MultipartFile file) {
//
//        System.out.println(userId);
//        String extName = file.getOriginalFilename().split("\\.")[1];
//        String fileName = file.getOriginalFilename().split("\\.")[0];
//        System.out.println(extName);
//        System.out.println(fileName);
//
//        byte[] bytes = null;
//        try {
//            bytes = file.getBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String path = FastDFSClientUtils.upload(bytes, extName);
//        return path;
//    }

    @ApiOperation(value = "多文件上传", notes = "")
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(@RequestParam Map<String, Object> paramMap,HttpServletRequest request){
        System.out.println(paramMap);
        String userId = (String)request.getAttribute("userId");
        return fileService.upload(userId,paramMap,request);
    }




}
