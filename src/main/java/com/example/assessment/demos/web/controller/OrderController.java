package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 订单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SysOrder> getOrderDetail(
            @PathVariable Long id
    ) {
        log.info("订单详情: {}", id);

        SysOrder sysOrder = orderService.getOrderDetail(id);

        return Result.success(sysOrder);
    }

    /**
     * 添加订单
     * @param addOrderDTO
     * @return
     */
    @PostMapping(value = "", consumes = "multipart/form-data")
    public Result<String> addOrder(
            @RequestPart AddOrderDTO addOrderDTO
    ) {
        log.info("添加订单: {}", addOrderDTO);

        // TODO 添加订单逻辑未完成
        orderService.addOrder(addOrderDTO);

        return Result.success("添加成功");
    }
}
