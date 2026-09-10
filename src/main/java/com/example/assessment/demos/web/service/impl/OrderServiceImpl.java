package com.example.assessment.demos.web.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.*;
import com.example.assessment.demos.web.mapper.CustomerMapper;
import com.example.assessment.demos.web.mapper.OrderMapper;
import com.example.assessment.demos.web.mapper.ProductMapper;
import com.example.assessment.demos.web.properties.AliOssProperties;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.OrderService;
import com.example.assessment.demos.web.utils.AliOssUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    private CustomerMapper customerMapper;

    /**
     * 订单查询
     *
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

        if (userId == 1) {
            orderPage = orderMapper.orderQueryAll(orderSearchDTO);
        } else {
            // 执行查询
            orderPage = orderMapper.orderQuery(orderSearchDTO);
        }


        return new PageResult(orderPage.getTotal(), orderPage.getResult());
    }

    /**
     * 订单详情
     *
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
     *
     * @param addOrderDTO
     * @return
     */
    @Override
    public void addOrder(AddOrderDTO addOrderDTO) throws IOException {
        if (addOrderDTO == null) {
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

    /**
     * 订单审核
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    @Transactional
    public void auditOrder(Long id, String status, String rejectReason) {
        // 参数校验
        if (id == null || status == null || "".equals(status)) {
            throw new RuntimeException("参数为空");
        }

        // 查询订单详细信息
        SysOrder sysOrder = orderMapper.getOrderDetail(id);
        // 取出商品id
        Long productId = sysOrder.getProductId();
        // 根据商品id查询商品库存
        Integer stock = productMapper.getProductById(productId);

        if (stock == null || stock <= 0 || stock < sysOrder.getQuantity()) {
            throw new RuntimeException("商品库存不足");
        }


        LocalDateTime updateTime = LocalDateTime.now();

        // 扣减库存
        productMapper.reduceStock(productId, sysOrder.getQuantity(), updateTime);


        // 执行订单审核
        orderMapper.auditOrder(id, status, updateTime, rejectReason);


    }

    /**
     * 重新提交订单
     *
     * @param id
     * @param addOrderDTO
     * @return
     */
    @Override
    public void updateOrder(Long id, AddOrderDTO addOrderDTO) {
        if (id == null || addOrderDTO == null) {
            throw new RuntimeException("参数为空");
        }
        // 获取当前时间
        LocalDateTime updateTime = LocalDateTime.now();
        // 根据id查询订单详情
        SysOrder sysOrder = orderMapper.getOrderDetail(id);
        // 数据拷贝
        BeanUtils.copyProperties(addOrderDTO, sysOrder);
        // 设置更新时间
        sysOrder.setUpdateTime(updateTime);
        // 设置顶端状态
        sysOrder.setStatus("待审批");
        if (sysOrder == null) {
            throw new RuntimeException("订单不存在");
        }

        // 执行更新订单
        orderMapper.updateOrder(id, sysOrder);
    }

    /**
     * 导出订单
     *
     * @param response
     * @param orderSearchDTO
     * @return
     */
    @Override
    public void exportOrder(HttpServletResponse response, OrderSearchDTO orderSearchDTO) throws IOException {

        Long userId = BaseContext.getCurrentId();
        orderSearchDTO.setUserId(userId);
        // 根据条件查询订单列表
        List<OrderExportExcelData> excelDataList;

        if (userId == 1) {
            excelDataList = orderMapper.exportOrderAll(orderSearchDTO);
        } else {
            excelDataList = orderMapper.exportOrder(orderSearchDTO);
        }


        if (excelDataList == null || excelDataList.size() == 0) {
            throw new RuntimeException("没有数据");
        }

        // 利用EasyExcel把查询出来的数据写入Excel文件
        EasyExcel.write(response.getOutputStream(), OrderExportExcelData.class).sheet("订单列表").doWrite(excelDataList);

    }

    /**
     * 导入订单
     *
     * @param file
     * @return
     */
    @Override
    @Transactional
    public void importOrder(MultipartFile file) throws IOException {
        List<OrderImportExcelData> excelDataList = EasyExcel.read(file.getInputStream())
                .head(OrderImportExcelData.class)
                .sheet()
                .doReadSync();

        if (excelDataList == null || excelDataList.isEmpty()) {
            throw new RuntimeException("导入数据为空");
        }

        Long currentUserId = BaseContext.getCurrentId();

        for (int i = 0; i < excelDataList.size(); i++) {
            OrderImportExcelData data = excelDataList.get(i);
            int rowNum = i + 2;

            if (data.getCustomerName() == null || data.getCustomerName().trim().isEmpty()) {
                throw new RuntimeException("第" + rowNum + "行：客户名称不能为空");
            }
            if (data.getProductName() == null || data.getProductName().trim().isEmpty()) {
                throw new RuntimeException("第" + rowNum + "行：商品名称不能为空");
            }
            if (data.getQuantity() == null || data.getQuantity() <= 0) {
                throw new RuntimeException("第" + rowNum + "行：数量必须大于0");
            }

            // 1. 通过客户名称查找客户ID
            Long customerId = customerMapper.getCustomerIdByName(data.getCustomerName().trim());
            if (customerId == null) {
                throw new RuntimeException("第" + rowNum + "行：未找到客户【" + data.getCustomerName() + "】");
            }

            // 2. 校验该客户是否属于当前销售员（管理员跳过校验）
            if (currentUserId != 1) {
                Long salesmanId = customerMapper.getSalesmanIdByCustomerId(customerId);
                if (!currentUserId.equals(salesmanId)) {
                    throw new RuntimeException("第" + rowNum + "行：客户【" + data.getCustomerName()
                            + "】不属于您，无法导入");
                }
            }

            // 3. 通过商品名称查找商品ID
            Long productId = productMapper.getProductIdByName(data.getProductName().trim());
            if (productId == null) {
                throw new RuntimeException("第" + rowNum + "行：未找到商品【" + data.getProductName() + "】");
            }

            // 4. 从数据库获取真实单价，计算金额
            BigDecimal unitPrice = productMapper.getProductPrice(productId);
            BigDecimal amount = unitPrice.multiply(BigDecimal.valueOf(data.getQuantity()));

            // 5. 生成订单编号
            long timestamp = System.currentTimeMillis();
            String uuid = java.util.UUID.randomUUID().toString();
            String orderNumber = timestamp + "__" + uuid;

            // 6. 构建DTO并插入
            AddOrderDTO addOrderDTO = new AddOrderDTO();
            addOrderDTO.setOrderNumber(orderNumber);
            addOrderDTO.setCustomerId(customerId);
            addOrderDTO.setProductId(productId);
            addOrderDTO.setQuantity(data.getQuantity());
            addOrderDTO.setPrice(unitPrice);
            addOrderDTO.setAmount(amount);
            addOrderDTO.setStatus(data.getStatus() != null ? data.getStatus() : "待审批");
            addOrderDTO.setFile(data.getFile());
            addOrderDTO.setCreateTime(LocalDateTime.now());
            addOrderDTO.setUpdateTime(LocalDateTime.now());

            orderMapper.addOrder(addOrderDTO);
        }

    }
}
