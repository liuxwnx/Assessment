package com.example.assessment.demos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderDTO {
    private String orderNumber; // 订单编号(时间戳+uuid，唯一)
    private Long customerId; // 客户id
    private Long productId;  // 商品id
    private int quantity; // 数量
    private String status; // 订单状态
    private BigDecimal price; // 单价 (分)
    private BigDecimal amount; // 金额（分）
    private String file; // 合同文件
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
