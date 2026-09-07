package com.example.assessment.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product { // 商品表


    // 商品id
    private Long id;

    // 商品名称
    private String productName;

    // 商品库存
    private Integer currentInventory;

    // 商品价格
    private BigDecimal unitPrice;

    // 创建时间
    private String createTime;

    // 更新时间
    private String updateTime;
}
