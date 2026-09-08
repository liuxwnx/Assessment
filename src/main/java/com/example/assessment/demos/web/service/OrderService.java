package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.result.PageResult;

public interface OrderService {
    /**
     * 订单查询
     * @param orderSearchDTO
     * @return
     */
    PageResult orderQuery(OrderSearchDTO orderSearchDTO);
}
