package com.example.assessment.demos.web.interceptor;

import com.example.assessment.demos.web.utils.JwtUtils;
import com.example.assessment.demos.web.context.BaseContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {



    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        System.out.println("当前线程的id" + Thread.currentThread().getId());

        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(JwtUtils.SECRET_KEY);

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);

            // 检查令牌是否在黑名单中
            String logoutKey = "token:" + token;
            /*if (redisTemplate.hasKey(logoutKey)) {
                log.info("令牌已在黑名单中，拒绝访问");
                response.setStatus(401);
                return false;
            }*/

            if (!redisTemplate.hasKey(logoutKey)) {
                log.info("令牌已失效，拒绝访问");
                response.setStatus(401);
                return false;
            }


            Claims claims = JwtUtils.parseToken(token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            BaseContext.setCurrentId(userId);
            log.info("当前用户id：{}", userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
