package com.example.assessment.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOrder { // 订单表
    // 订单id
    private Long id;

    // 订单编号
    private String orderNumber;

    // 客户id
    private Long customerId;

    // 商品id
    private Long productId;

    // 订单金额
    private BigDecimal amount;

    // 状态
    private String status;

    // 合同文件
    private Blob file;

    // 驳回理由
    private String rejectReason;

    // 数量
    private Integer quantity;

    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}
