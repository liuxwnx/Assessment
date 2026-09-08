package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.entity.Product;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface ProductMapper {
    /**
     * 根据商品ID获取商品价格
     * @param productId
     * @return
     */
    @Select("SELECT unit_price FROM product WHERE id = #{productId}")
    BigDecimal getProductPrice(Long productId);

    /**
     * 分页查询商品列表
     * @param productDTO
     * @return
     */
    Page<Product> list(ProductDTO productDTO);
}
