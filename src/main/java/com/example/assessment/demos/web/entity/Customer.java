package com.example.assessment.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer { // 用户表

    //客户id
    private Long id;

    // 客户名称
    private String customerName;

    // 客户手机号
    private String customerPhone;

    // 公司名称
    private String companyName;

    // 客户地址
    private String customerAddress;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
