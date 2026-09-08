package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.mapper.OrderMapper;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

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
}
