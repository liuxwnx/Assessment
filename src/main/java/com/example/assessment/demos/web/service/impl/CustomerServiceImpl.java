package com.example.assessment.demos.web.service.impl;

import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.dto.CustomerDTO;
import com.example.assessment.demos.web.entity.Customer;
import com.example.assessment.demos.web.mapper.CustomerMapper;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.service.CustomerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 分页查询客户列表
     *
     * @param customerDTO 客户DTO
     * @return 客户列表
     */
    @Override
    public PageResult list(CustomerDTO customerDTO) {

        PageHelper.startPage(customerDTO.getPageNum(), customerDTO.getPageSize());


        Page<Customer> page = null;
        // 获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        // 设置用户ID到DTO中
        customerDTO.setUserId(userId);
        if (userId == 1){
            page = customerMapper.listAll(customerDTO);
        }else {
            page = customerMapper.listByUserId(customerDTO);
        }



        return new PageResult(page.getTotal(), page.getResult());
    }
}
