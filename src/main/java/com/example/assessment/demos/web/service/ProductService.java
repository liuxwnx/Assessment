package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.result.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    /**
     * 分页查询商品列表
     */
    PageResult list(ProductDTO productDTO);

    /**
     * 导入商品数据
     */
    void importProduct(MultipartFile file) throws IOException;
}
