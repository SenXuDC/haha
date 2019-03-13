package com.xusenme.controller;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;
import com.xusenme.utils.FastDFSClientUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FileController {

    @ApiOperation(value = "上传文件", notes = "")
    @RequestMapping(value = "/upload/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@PathVariable("userId") String userId, @RequestParam("file") MultipartFile file) {

        System.out.println(userId);
        String extName = file.getOriginalFilename().split("\\.")[1];
        String fileName = file.getOriginalFilename().split("\\.")[0];
        System.out.println(extName);
        System.out.println(fileName);

        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = FastDFSClientUtils.upload(bytes, extName);
        return path;
    }

    @ApiOperation(value = "多文件上传", notes = "")
    @RequestMapping(value="/batch/upload", method=RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(HttpServletRequest request){
        List<String> resultList = new ArrayList<>();
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
//        BufferedOutputStream stream = null;
        for (int i =0; i< files.size(); ++i) {
            file = files.get(i);
            String extName = file.getOriginalFilename().split("\\.")[1];
            String fileName = file.getOriginalFilename().split("\\.")[0];
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    resultList.add(FastDFSClientUtils.upload(bytes, extName));
//                    stream =
//                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
//                    stream.write(bytes);
//                    stream.close();
                } catch (Exception e) {
//                    stream =  null;
                    e.printStackTrace();
                }
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        return resultList;

    }


}
