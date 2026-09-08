package com.example.assessment.demos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long userId; // 用户ID
    private String customerName; // 客户名
    private int pageNum; // 当前页码
    private int pageSize; // 每页大小
}
