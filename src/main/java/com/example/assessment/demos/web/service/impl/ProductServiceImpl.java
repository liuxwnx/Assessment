package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.entity.Product;
import com.example.assessment.demos.web.mapper.ProductMapper;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
