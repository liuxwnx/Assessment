package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.ProductDTO;
import com.example.assessment.demos.web.entity.Product;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    /**
     * 根据商品ID获取商品库存
     * @param id
     * @return
     */
    @Select("SELECT current_inventory FROM product WHERE id = #{id}")
    Integer getProductById(Long id);

    /**
     * 扣减商品库存
     * @param productId
     * @param quantity
     */
    void reduceStock(@Param("productId") Long productId, @Param("quantity") Integer quantity, @Param("updateTime")LocalDateTime updateTime);

    /**
     * 根据商品名称获取商品ID
     * @param productName
     * @return
     */
    @Select("SELECT id FROM product WHERE product_name = #{productName}")
    Long getProductIdByName(@Param("productName") String productName);
}
