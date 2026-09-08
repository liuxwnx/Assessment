package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.AddOrderDTO;
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

    /**
     * 订单详情
     * @param id
     * @return
     */
    @Select("SELECT * FROM sys_order WHERE id = #{id}")
    SysOrder getOrderDetail(Long id);

    /**
     * 添加订单
     * @param addOrderDTO
     * @return
     */
    void addOrder(AddOrderDTO addOrderDTO);
}
