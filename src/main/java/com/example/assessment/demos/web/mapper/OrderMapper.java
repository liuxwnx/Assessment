package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.entity.OrderExportExcelData;
import com.example.assessment.demos.web.entity.SysOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
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

    /**
     * 订单审核
     * @param id
     * @param status
     * @param updateTime
     * @return
     */
    void auditOrder(@Param("id") Long id, @Param("status") String status, @Param("updateTime") LocalDateTime updateTime, @Param("rejectReason") String rejectReason);

    /**
     * 修改订单
     * @param id
     * @param sysOrder
     * @return
     */
    void updateOrder(@Param("id") Long id, @Param("sysOrder") SysOrder sysOrder);

    /**
     * 导出订单
     * @param orderSearchDTO
     * @return
     */
    List<OrderExportExcelData> exportOrder(OrderSearchDTO orderSearchDTO);

    /**
     * 导出全部订单
     * @param orderSearchDTO
     * @return
     */
    List<OrderExportExcelData> exportOrderAll(OrderSearchDTO orderSearchDTO);
}
