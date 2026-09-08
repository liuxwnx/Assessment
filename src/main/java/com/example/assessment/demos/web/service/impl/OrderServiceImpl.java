package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.mapper.OrderMapper;
import com.example.assessment.demos.web.mapper.ProductMapper;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.OrderService;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Value("${file.upload-path}")
    private String uploadPath;

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
    public void addOrder(AddOrderDTO addOrderDTO) {
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
        addOrderDTO.setOrderNumber(timestamp + uuid);
        // 设置订单状态
        addOrderDTO.setStatus("待审批");
        // 单价乘以数量
        BigDecimal yuan = productPrice.multiply(BigDecimal.valueOf(addOrderDTO.getQuantity()));
        // 单位转换为分
        BigDecimal amount = yuan.multiply(new BigDecimal(100));
        // 设置订单金额
        addOrderDTO.setAmount(amount);

        // 处理文件上传
        String filePath = uploadFile(addOrderDTO.getFile());
        // 将文件路径设置到DTO中（用于入库）
        addOrderDTO.setFilePath(filePath);

        orderMapper.addOrder(addOrderDTO);

    }

    private String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 按日期创建子目录: uploads/2026/09/08/
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        File dir = new File(uploadPath + dateDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 生成唯一文件名: 时间戳_原文件名
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;

        File dest = new File(dir, fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new RuntimeException("文件上传失败");
        }

        // 返回相对路径（存入数据库）
        return dateDir + "/" + fileName;
    }
}
