package com.xusenme.controller;

import com.xusenme.controller.vo.UserVo;
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
}
