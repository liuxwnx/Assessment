package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.dto.LogoutDTO;
import com.example.assessment.demos.web.entity.SysUser;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.LoginService;
import com.example.assessment.demos.web.vo.LoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody SysUser sysUser){
        log.info("登陆前login信息: {}", sysUser);

        LoginInfoVO loginInfoVO = loginService.login(sysUser);

        if (loginInfoVO != null){
            log.info("登录成功: {}", loginInfoVO);
            return Result.success(loginInfoVO, "登录成功");
        }

        return Result.error("用户名或密码错误");
    }

    /**
     * 退出登录
     * @param logoutDTO
     * @return
     */
    @PostMapping("/logout")
    public Result logout(@RequestBody LogoutDTO logoutDTO){
        log.info("退出登录: {}", logoutDTO.getToken());
        loginService.logout(logoutDTO.getToken());
        return Result.success("退出登录");
    }

}
