package com.example.assessment.demos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;


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
    private MultipartFile file; // 合同文件
    private String filePath; // 合同文件路径
}
