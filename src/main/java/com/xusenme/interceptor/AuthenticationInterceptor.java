package com.xusenme.interceptor;

import com.mysql.jdbc.StringUtils;
import com.xusenme.service.UserService;
import com.xusenme.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * jimisun
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    // key
    @Value("${user.jwtKey}")
    private String jwtKey;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        String path = httpServletRequest.getServletPath();
        if (path.startsWith("/user") || path.contains("swagger") || path.contains("api") || path.contains("/")) {
            return true;
        }


        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isNullOrEmpty(token)) {
            httpServletResponse.sendError(403,"没有权限认证");
            return false;
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token, jwtKey);
        } catch (ExpiredJwtException e) {
            httpServletResponse.sendError(403,"权限过期");
        }
        if (path.startsWith("/admin") && !(Boolean)claims.get("admin")) {
            httpServletResponse.sendError(403,"无访问权限");
            return false;
        }
        if (claims.get("id") != null) {
            return true;
        }
        httpServletResponse.sendError(403,"没有权限认证");
        return false;
//        HandlerMethod handlerMethod = (HandlerMethod) object;
//        Method method = handlerMethod.getMethod();

//        //检查是否有LoginToken注释，有则跳过认证
//        if (method.isAnnotationPresent(LoginToken.class)) {
//            LoginToken loginToken = method.getAnnotation(LoginToken.class);
//            if (loginToken.required()) {
//                return true;
//            }
//        }

//        //检查有没有需要用户权限的注解
//        if (method.isAnnotationPresent(CheckToken.class)) {
//            CheckToken checkToken = method.getAnnotation(CheckToken.class);
//            if (checkToken.required()) {
//                // 执行认证
//                if (token == null) {
//                    throw new RuntimeException("无token，请重新登录");
//                }
//                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getClaim("id").asString();
//                } catch (JWTDecodeException j) {
//                    throw new RuntimeException("访问异常！");
//                }
//                User user = userService.findUserById(userId);
//                if (user == null) {
//                    throw new RuntimeException("用户不存在，请重新登录");
//                }
//                Boolean verify = JwtUtil.isVerify(token, user);
//                if (!verify) {
//                    throw new RuntimeException("非法访问！");
//                }
//                return true;
//            }
//        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
