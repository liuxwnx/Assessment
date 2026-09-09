package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.mapper.OrderMapper;
import com.example.assessment.demos.web.mapper.ProductMapper;
import com.example.assessment.demos.web.properties.AliOssProperties;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.OrderService;
import com.example.assessment.demos.web.utils.AliOssUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AliOssProperties properties;

    /**
     * 订单查询
     * @param orderSearchDTO
     * @return
     */
    @Override
    public PageResult orderQuery(OrderSearchDTO orderSearchDTO) {

        // 设置分页参数
        PageHelper.startPage(orderSearchDTO.getPageNum(), orderSearchDTO.getPageSize());

        // 获取当前用户ID
        Long userId = BaseContext.getCurrentId();

        // 设置用户ID
        orderSearchDTO.setUserId(userId);

        Page<SysOrder> orderPage = null;

        if (userId == 1){
            orderPage = orderMapper.orderQueryAll(orderSearchDTO);
        }else {
            // 执行查询
            orderPage = orderMapper.orderQuery(orderSearchDTO);
        }


        return new PageResult(orderPage.getTotal(), orderPage.getResult());
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @Override
    public SysOrder getOrderDetail(Long id) {

        // 根据ID查询订单详情
        SysOrder sysOrder = orderMapper.getOrderDetail(id);

        return sysOrder;
    }

    /**
     * 添加订单
     * @param addOrderDTO
     * @return
     */
    @Override
    public void addOrder(AddOrderDTO addOrderDTO) throws IOException {
        if (addOrderDTO == null){
            throw new RuntimeException("参数为空");
        }

        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        // 生成uuid
        String uuid = java.util.UUID.randomUUID().toString();

        // 获取商品单价
        BigDecimal productPrice = productMapper.getProductPrice(addOrderDTO.getProductId());

        // 设置商品单价
        addOrderDTO.setPrice(productPrice);
        // 设置订单编号
        addOrderDTO.setOrderNumber(timestamp + "__" + uuid);
        // 设置订单状态
        addOrderDTO.setStatus("待审批");
        // 单价乘以数量
        BigDecimal yuan = productPrice.multiply(BigDecimal.valueOf(addOrderDTO.getQuantity()));
        // 设置订单金额
        addOrderDTO.setAmount(yuan);

        // 设置创建时间
        addOrderDTO.setCreateTime(LocalDateTime.now());
        // 设置更新时间
        addOrderDTO.setUpdateTime(LocalDateTime.now());

        orderMapper.addOrder(addOrderDTO);

    }

}
