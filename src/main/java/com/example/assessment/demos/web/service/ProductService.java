package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.result.PageResult;

public interface ProductService {
    /**
     * 分页查询商品列表
     */
    PageResult list(ProductDTO productDTO);
}
