package com.example.assessment.demos.web.controller;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品列表
     */
    @GetMapping("/list")
    public Result<PageResult> list(
            ProductDTO productDTO
    ) {
        log.info("productDTO: {}", productDTO);
        PageResult pageResult = productService.list(productDTO);
        return Result.success(pageResult);
    }
}
