package com.xusenme.controller;

import com.xusenme.controller.vo.MyFileVo;
import com.xusenme.controller.vo.ShareVo;
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
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        System.out.println(paramMap);
        String userId = (String) request.getAttribute("userId");
        return fileService.upload(userId, paramMap, request);
    }

    @ApiOperation(value = "文件列表", notes = "")
    @RequestMapping(value = "/listDir", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object listDir(@RequestBody MyFileVo myFileVo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return fileService.listDir(userId, myFileVo.getDir());
    }


    @ApiOperation(value = "回收文件列表", notes = "")
    @RequestMapping(value = "/listRecycle", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object listRecycle(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return fileService.listRecycle(userId);
    }

    @ApiOperation(value = "恢复文件", notes = "")
    @RequestMapping(value = "/recoveryFile/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object recoveryFile(@PathVariable String id, HttpServletRequest request
            , HttpServletResponse response) {
        String userId = (String) request.getAttribute("userId");
        return fileService.recoveryFile(id);
    }



    @ApiOperation(value = "创建文件夹", notes = "")
    @RequestMapping(value = "/createFolder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createFolder(@RequestBody MyFileVo myFileVo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return fileService.createFolder(userId, myFileVo.getFolder(), myFileVo.getDir());
    }

    @ApiOperation(value = "下载文件", notes = "")
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String download(@PathVariable String id, HttpServletRequest request
            , HttpServletResponse response) {
        String userId = (String) request.getAttribute("userId");
        fileService.download(id, response);
        return null;
    }

    @ApiOperation(value = "删除文件或者文件夹", notes = "")
    @RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteFile(@PathVariable String id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return fileService.deleteFile(id);
    }

    @ApiOperation(value = "文件分享", notes = "")
    @RequestMapping(value = "/shareFile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object shareFile(@RequestBody ShareVo shareVo , HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return fileService.shareFile(shareVo);
    }


    @ApiOperation(value = "查看文件分享", notes = "")
    @RequestMapping(value = "/shareGet", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object shareGet(@RequestBody ShareVo shareVo , HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        shareVo.setEmail(email);
        return fileService.shareGet(shareVo);
    }



}
