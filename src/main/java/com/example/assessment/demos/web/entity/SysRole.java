package com.example.assessment.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {  // 角色表
    private Long id;  // 角色ID

    private String roleName;  // 角色名称

    private LocalDateTime createTime;  // 创建时间

    private LocalDateTime updateTime;  // 更新时间
}
