package com.example.assessment.demos.web.mapper;

import com.example.assessment.demos.web.dto.CustomerDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

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
}
