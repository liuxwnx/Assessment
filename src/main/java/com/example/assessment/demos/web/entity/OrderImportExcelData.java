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
public class OrderImportExcelData {

    @ExcelProperty("客户名称")
    @ColumnWidth(20)
    private String customerName;

    @ExcelProperty("商品名称")
    @ColumnWidth(20)
    private String productName;

    @ExcelProperty("数量")
    @ColumnWidth(10)
    private Integer quantity;

    @ExcelProperty("单价")
    @ColumnWidth(10)
    private BigDecimal unitPrice;

    @ExcelProperty("金额")
    @ColumnWidth(15)
    private BigDecimal amount;

    @ExcelProperty("状态")
    @ColumnWidth(10)
    private String status;

    @ExcelProperty("文件")
    @ColumnWidth(20)
    private String file;

}