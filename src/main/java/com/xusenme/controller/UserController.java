package com.xusenme.controller;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;
import com.xusenme.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        return userService.getUserById(user);
    }

    @ApiOperation(value = "用户注册", notes = "")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> createUser(@RequestBody UserVo userVo) {
        // 用户提供邮箱
        return userService.createUser(userVo);
    }

    @ApiOperation(value = "用户激活", notes = "")
    @RequestMapping(value = "/active/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> active(@PathVariable("id") String id) {
        return userService.active(id);
    }

    @ApiOperation(value = "用户登录", notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> login(@RequestBody UserVo userVo) {
        return userService.login(userVo);
    }

}
