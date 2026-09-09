package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.dto.StatusDTO;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    @PostMapping()
    public Result<String> addOrder(
            @RequestBody AddOrderDTO addOrderDTO
    ) throws IOException {
        log.info("添加订单: {}", addOrderDTO);

        // TODO 添加订单逻辑未完成
        orderService.addOrder(addOrderDTO);

        return Result.success("添加成功");
    }

    /**
     * 订单审核
     * @param id
     * @param statusDTO
     * @return
     */
    @PostMapping("/{id}/audit")
    public Result<String> auditOrder(
            @PathVariable Long id,
            @RequestBody StatusDTO statusDTO
            ) {
        log.info("订单审核: {}", id);

        orderService.auditOrder(id, statusDTO.getStatus(), statusDTO.getRejectReason()  );

        return Result.success("审核成功");
    }

    /**
     * 修改订单
     * @param id
     * @param addOrderDTO
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> updateOrder(
            @PathVariable Long id,
            @RequestBody AddOrderDTO addOrderDTO
    ) {
        log.info("修改订单: {}", id);
        orderService.updateOrder(id, addOrderDTO);
        return Result.success("修改成功");
    }
}
