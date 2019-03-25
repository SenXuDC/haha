package com.xusenme.service.impl;

import com.xusenme.controller.vo.UserVo;
import com.xusenme.dao.UserDao;
import com.xusenme.model.User;
import com.xusenme.service.UserService;
import com.xusenme.utils.Email;
import com.xusenme.utils.JwtUtil;
import com.xusenme.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
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
        try {
            user.setPassword(Md5.encoderByMd5(userVo.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
    public String active(String id) {
        Map<String, String> result = new HashMap<>();
        String resp = "";
        if (userDao.active(id) > 0) {
            // 删除多余的用户信息
//            result.put("result", "true");
            resp = "恭喜您，激活用户成功";
        } else {
//            result.put("result", "false");
//            result.put("message", "激活失败");
            resp = "对不起，激活用户失败";
        }
        return resp;
    }

    @Override
    public Map<String, String> login(UserVo userVo) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            userVo.setPassword(Md5.encoderByMd5(userVo.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user = userDao.getUserByEmailAndPassword(userVo);
        if (user == null) {
            resultMap.put("error_type", "error_bad_request");
            resultMap.put("error_info", "用户名或者密码错误");
            return resultMap;
        }
        String jwt = JwtUtil.createJWT(expired * 60 * 1000, user, jwtKey);
        resultMap.put("token", jwt);
        return resultMap;
    }

    @Override
    public Object changePassword(UserVo userVo) {
        try {
            userVo.setOldPassword(Md5.encoderByMd5(userVo.getOldPassword()));
            userVo.setNewPassword(Md5.encoderByMd5(userVo.getNewPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (userDao.changePassword(userVo) > 0) {
            return "密码修改成功";
        }
        return "密码修改失败";
    }
}
