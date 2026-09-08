package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.entity.SysOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 订单查询
     * @param orderSearchDTO
     * @return
     */
    Page<SysOrder> orderQuery(OrderSearchDTO orderSearchDTO);

    /**
     * 查询全部订单
     * @param orderSearchDTO
     * @return
     */
    Page<SysOrder> orderQueryAll(OrderSearchDTO orderSearchDTO);
}
