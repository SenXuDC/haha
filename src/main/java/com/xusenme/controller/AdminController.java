package com.xusenme.controller;

import com.xusenme.controller.vo.UpdateUserVo;
import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.MyFile;
import com.xusenme.model.User;
import com.xusenme.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @ApiOperation(value = "管理员查看用户列表", notes = "")
    @RequestMapping(value = "/listUser", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<User> listUser() {
        return adminService.listUser();
    }


    @ApiOperation(value = "管理员禁用用户,或修改用户的存储空间大小", notes = "")
    @RequestMapping(value = "/updateUser", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUser(@RequestBody UpdateUserVo updateUserVo) {
        return adminService.updateUser(updateUserVo);
    }

    @ApiOperation(value = "管理员查看文件列表", notes = "")
    @RequestMapping(value = "/listFile", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<MyFile> listFile() {
        return adminService.listMyFile();
    }


    @ApiOperation(value = "管理员禁用或启动文件", notes = "")
    @RequestMapping(value = "/updateFile", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object updateMyFile(@RequestBody MyFile myFile) {
        return adminService.updateMyFile(myFile);
    }

}
