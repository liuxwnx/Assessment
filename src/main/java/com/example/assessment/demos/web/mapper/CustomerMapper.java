package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.CustomerDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {
    /**
     * 根据用户ID分页查询客户列表
     *
     * @param customerDTO 客户DTO
     * @return 客户列表
     */
    Page<Customer> listByUserId(CustomerDTO customerDTO);

    /**
     * 分页查询所有客户列表
     *
     * @param customerDTO 客户DTO
     * @return 客户列表
     */
    Page<Customer> listAll(CustomerDTO customerDTO);

    /**
     * 根据客户名称查询客户ID
     *
     * @param customerName 客户名称
     * @return 客户ID
     */
    @Select("SELECT id FROM customer WHERE customer_name = #{customerName}")
    Long getCustomerIdByName(@Param("customerName") String customerName);

    /**
     * 根据客户ID查询业务员ID
     *
     * @param customerId 客户ID
     * @return 业务员ID
     */
    @Select("SELECT salesman_id FROM customer WHERE id = #{customerId}")
    Long getSalesmanIdByCustomerId(@Param("customerId") Long customerId);
}
