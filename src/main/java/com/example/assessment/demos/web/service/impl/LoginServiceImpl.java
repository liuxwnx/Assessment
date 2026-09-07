package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.entity.SysUser;
import com.example.assessment.demos.web.mapper.LoginMapper;
import com.example.assessment.demos.web.service.LoginService;
import com.example.assessment.demos.web.utils.JwtUtils;
import com.example.assessment.demos.web.vo.LoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 登录
     * @param sysUser
     * @return
     */
    @Override
    public LoginInfoVO login(SysUser sysUser) {

        // md5加密
        String password = sysUser.getPassword();

        sysUser.setPassword(DigestUtils.md5Hex(password));


        // 用户名和密码
        log.info("username: {}, password: {}", sysUser.getUsername(), password);

        SysUser user = loginMapper.selectByUsernameAndPassword(sysUser);

        if (user != null) {

            // 将员工信息存入令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("username", user.getUsername());

            // 生成令牌
            String token = JwtUtils.generateToken(claims);

            return LoginInfoVO.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roleId(user.getRoleId())
                    .token(token)
                    .build();
        }

        return null;
    }
}
