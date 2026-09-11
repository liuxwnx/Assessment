package com.example.assessment.demos.web.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.entity.Product;
import com.example.assessment.demos.web.entity.ProductImportExcelData;
import com.example.assessment.demos.web.mapper.ProductMapper;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 分页查询商品列表
     */
    @Override
    public PageResult list(ProductDTO productDTO) {
        PageHelper.startPage(productDTO.getPageNum(), productDTO.getPageSize());

        Page<Product> result =  productMapper.list(productDTO);

        return new PageResult(result.getTotal(), result.getResult());
    }

    /**
     * 导入商品数据
     */
    @Override
    public void importProduct(MultipartFile file) throws IOException {
        List<ProductImportExcelData> excelDataList = EasyExcel.read(file.getInputStream())
                .head(ProductImportExcelData.class)
                .sheet()
                .doReadSync();

        if (excelDataList == null || excelDataList.isEmpty()) {
            throw new RuntimeException("导入数据为空");
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < excelDataList.size(); i++) {
            ProductImportExcelData data = excelDataList.get(i);
            int rowNum = i + 2;

            if (data.getProductName() == null || data.getProductName().trim().isEmpty()) {
                log.warn("第{}行：商品名称为空，跳过", rowNum);
                failCount++;
                continue;
            }

            if (data.getQuantity() == null || data.getQuantity() <= 0) {
                log.warn("第{}行：数量为非正整数或为空，跳过", rowNum);
                failCount++;
                continue;
            }

            if (data.getUnitPrice() == null || data.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("第{}行：单价为非正数或为空，跳过", rowNum);
                failCount++;
                continue;
            }

            Long productId = productMapper.getProductIdByName(data.getProductName().trim());
            if (productId == null) {
                log.warn("第{}行：未找到商品【{}】，跳过", rowNum, data.getProductName());
                failCount++;
                continue;
            }

            productMapper.importUpdateProduct(productId, data.getQuantity(), data.getUnitPrice().multiply(new BigDecimal(100)), LocalDateTime.now());
            successCount++;
        }

        if (successCount == 0) {
            throw new RuntimeException("没有有效的导入数据");
        }

        log.info("导入完成：成功{}条，失败{}条", successCount, failCount);
    }
}
