package com.example.assessment.demos.web.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductImportExcelData {


    @ExcelProperty("商品名称")
    @ColumnWidth(20)
    private String productName;

    @ExcelProperty("数量")
    @ColumnWidth(10)
    private Integer quantity;

    @ExcelProperty("单价")
    @ColumnWidth(10)
    private BigDecimal unitPrice;
}
