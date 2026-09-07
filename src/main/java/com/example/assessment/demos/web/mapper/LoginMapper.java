package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    /**
     * 根据用户名和密码查询用户
     * @param sysUser
     * @return
     */
    SysUser selectByUsernameAndPassword(SysUser sysUser);
}
