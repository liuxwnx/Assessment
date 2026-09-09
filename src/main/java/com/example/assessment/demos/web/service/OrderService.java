package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.result.PageResult;

import java.io.IOException;

public interface OrderService {
    /**
     * 订单查询
     * @param orderSearchDTO
     * @return
     */
    PageResult orderQuery(OrderSearchDTO orderSearchDTO);

    /**
     * 订单详情
     * @param id
     * @return
     */
    SysOrder getOrderDetail(Long id);

    /**
     * 添加订单
     * @param addOrderDTO
     * @return
     */
    void addOrder(AddOrderDTO addOrderDTO) throws IOException;

    /**
     * 订单审核
     * @param id
     * @param status
     * @return
     */
    void auditOrder(Long id, String status, String rejectReason);

    /**
     * 修改订单
     * @param id
     * @param addOrderDTO
     * @return
     */
    void updateOrder(Long id, AddOrderDTO addOrderDTO);
}
