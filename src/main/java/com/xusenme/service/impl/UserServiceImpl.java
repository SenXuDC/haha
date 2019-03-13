package com.xusenme.service.impl;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.dao.UserDao;
import com.xusenme.model.User;
import com.xusenme.service.UserService;
import com.xusenme.utils.Email;
import com.xusenme.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    //发送的邮箱 内部代码只适用qq邮箱
    @Value("${email.serverEmailUser}")
    private String serverUserEmail;
    //授权密码 通过QQ邮箱设置->账户->POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务->开启POP3/SMTP服务获取
    @Value("${email.serverEmailPassword}")
    private String pwd;

    @Value("${host.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Value("${user.defaultSize}")
    private int size;

    // token 生效时间
    @Value("${user.expired}")
    private int expired;

    // key
    @Value("${user.jwtKey}")
    private String jwtKey;


    @Override
    public User getUserById(User user) {
        return userDao.getNameById(user);
    }

    @Override
    public Map<String, String> createUser(UserVo userVo) {
        Map<String, String> result = new HashMap<>();
        // 检查该邮件是否可用
        User beforeUser = userDao.getUserByEmail(userVo);
        if (beforeUser != null) {
            if (beforeUser.getActive()) {
                result.put("result", "false");
                result.put("message", "该邮箱已被使用");
                return result;
            } else {
                new Email(serverUserEmail, pwd).sendMessageTo(userVo.getEmail(), "haha网盘注册"
                        , "欢迎注册haha网盘，点击链接跳转完成注册: http://" + ip + ":" + port + "/user/active/" + beforeUser.getId()
                );
                result.put("message", "已重新发送邮件");
                return result;
            }
        }

        String uuid = UUID.randomUUID().toString();
        // 将用户名
        // 并发送一封电子邮件
        new Email(serverUserEmail, pwd).sendMessageTo(userVo.getEmail(), "haha网盘注册"
                , "欢迎注册haha网盘，点击链接跳转完成注册: http://" + ip + ":" + port + "/user/active/" + uuid
        );
        User user = new User();
        user.setId(uuid);
        user.setUsername(userVo.getUsername());
        user.setEmail(userVo.getEmail());
        user.setPassword(userVo.getPassword());
        user.setSize(size);
        if (userDao.createUser(user) < 0) {
            result.put("result", "false");
            result.put("message", "存入数据库失败");
            return result;
        }
        result.put("result", "true");
        result.put("message", "注册成功，请查看邮件激活");
        return result;
    }


    @Override
    public Map<String, String> active(String id) {
        Map<String, String> result = new HashMap<>();
        if (userDao.active(id) > 0) {
            // 删除多余的用户信息
            result.put("result", "true");

        } else {
            result.put("result", "false");
            result.put("message", "激活失败");
        }
        return result;
    }

    @Override
    public Map<String, String> login(UserVo userVo) {
        Map<String, String> resultMap = new HashMap<>();
        User user = userDao.getUserByEmailAndPassword(userVo);
        if (user == null) {
            resultMap.put("result", "false");
            resultMap.put("message", "用户名或者密码错误");
            return resultMap;
        }
        String jwt = JwtUtil.createJWT(expired * 60 * 1000, user, jwtKey);
        resultMap.put("token", jwt);
        return resultMap;
    }
}
