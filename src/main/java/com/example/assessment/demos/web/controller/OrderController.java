package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 订单查询
     * @param orderSearchDTO
     * @return
     */
    @GetMapping("/list")
    public Result<PageResult> orderQuery(
             OrderSearchDTO orderSearchDTO
    ) {
        log.info("订单查询: {}", orderSearchDTO);

        PageResult pageResult = orderService.orderQuery(orderSearchDTO);

        return Result.success(pageResult);
    }

}
