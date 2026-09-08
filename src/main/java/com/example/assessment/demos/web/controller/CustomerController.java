package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.dto.CustomerDTO;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController { // 客户

    @Autowired
    private CustomerService customerService;

    /**
     * 分页查询客户列表
     *
     * @param customerDTO 客户DTO
     * @return 客户列表
     */
    @GetMapping("/list")
    public Result<PageResult> list(
            CustomerDTO customerDTO
    ) {
        log.info("customerDTO: {}", customerDTO);

        PageResult pageResult = customerService.list(customerDTO);

        return Result.success(pageResult);
    }
}
