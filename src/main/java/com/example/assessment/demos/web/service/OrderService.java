package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.result.PageResult;

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
    void addOrder(AddOrderDTO addOrderDTO);
}
