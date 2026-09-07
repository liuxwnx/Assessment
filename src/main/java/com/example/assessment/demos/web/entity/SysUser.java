package com.example.assessment.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser { // 用户表
    private Long id; // 用户ID
    private String username; // 用户名
    private String password; // 密码
    private String gender; // 性别
    private String phone; // 电话
    private Long roleId; // 角色id
    private String remarks; // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
