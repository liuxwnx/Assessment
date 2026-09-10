package com.example.assessment.demos.web.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderExportExcelData {

    // 订单编号
    @ExcelProperty("订单编号")
    @ColumnWidth(20)
    private String orderNumber;

    // 客户名称
    @ExcelProperty("客户名称")
    @ColumnWidth(20)
    private String customerName;

    // 商品名称
    @ExcelProperty("商品名称")
    @ColumnWidth(20)
    private String productName;

    // 数量
    @ExcelProperty("数量")
    @ColumnWidth(10)
    private Integer quantity;

    // 单价
    @ExcelProperty("单价(分)")
    @ColumnWidth(15)
    private BigDecimal unitPrice;

    // 金额
    @ExcelProperty("金额(分)")
    @ColumnWidth(10)
    private BigDecimal amount;

    // 状态
    @ExcelProperty("状态")
    @ColumnWidth(10)
    private String status;

    // 合同文件
    @ExcelProperty("合同文件")
    @ColumnWidth(20)
    private String file;

    // 创建时间
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createTime;

}
