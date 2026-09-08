package com.example.assessment.demos.web.service;

import com.example.assessment.demos.web.dto.CustomerDTO;
import com.example.assessment.demos.web.result.PageResult;

public interface CustomerService {
    /**
     * 分页查询客户列表
     *
     * @param customerDTO 客户DTO
     * @return 客户列表
     */
    PageResult list(CustomerDTO customerDTO);
}
