package com.example.assessment.demos.web.service;


import com.example.assessment.demos.web.entity.SysUser;
import com.example.assessment.demos.web.vo.LoginInfoVO;
import org.apache.ibatis.annotations.Param;

public interface LoginService {
    /**
     * 登录
     * @param sysUser
     * @return
     */
    LoginInfoVO login(@Param("sysUser") SysUser sysUser);
}
