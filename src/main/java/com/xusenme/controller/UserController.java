package com.xusenme.controller;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.model.User;
import com.xusenme.service.UserService;
import com.xusenme.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // key
    @Value("${user.jwtKey}")
    private String jwtKey;

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
    public Object active(@PathVariable("id") String id) {
        return userService.active(id);
    }

    @ApiOperation(value = "用户登录", notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(@RequestBody UserVo userVo) {
        Map<String,String> result = userService.login(userVo);
        if (result.get("error_type")!=null) {
            return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @ApiOperation(value = "用户详情", notes = "")
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        String token = req.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token, jwtKey);
        resultMap.put("id", claims.get("id"));
        resultMap.put("username", claims.get("username"));
        resultMap.put("email", claims.get("email"));
        resultMap.put("admin", claims.get("admin"));
        resultMap.put("size", claims.get("size"));
        return resultMap;
    }

//    changePassword
    @ApiOperation(value = "用户修改密码", notes = "")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object changePassword(@RequestBody UserVo userVo) {
        return userService.changePassword(userVo);
//        Map<String,String> result = userService.login(userVo);
//        if (result.get("error_type")!=null) {
//            return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
//        }
//        return result;
    }
}
