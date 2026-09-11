package com.example.assessment.demos.web.controller;


import com.alibaba.excel.EasyExcel;
import com.example.assessment.demos.web.dto.AddOrderDTO;
import com.example.assessment.demos.web.dto.OrderSearchDTO;
import com.example.assessment.demos.web.dto.StatusDTO;
import com.example.assessment.demos.web.entity.OrderImportExcelData;
import com.example.assessment.demos.web.entity.ProductImportExcelData;
import com.example.assessment.demos.web.entity.SysOrder;
import com.example.assessment.demos.web.result.PageResult;
import com.example.assessment.demos.web.result.Result;
import com.example.assessment.demos.web.service.OrderService;
import com.example.assessment.demos.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

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

    /**
     * 导出订单
     * @param response
     * @return
     */
    @GetMapping("/export")
    public Result<String> exportOrder(HttpServletResponse response, OrderSearchDTO orderSearchDTO) throws IOException {
        log.info("导出订单: {}", orderSearchDTO);
        // 调用服务层方法导出订单
        orderService.exportOrder(response, orderSearchDTO);
        // 使用easyExcel导出订单
        return Result.success("导出成功");
    }

    /**
     * 导入订单
     * @param file
     * @return
     */
   /* @PostMapping("/import")
    public Result<String> importOrder(MultipartFile file) throws IOException {
        log.info("导入订单");
        // 使用easyExcel导入订单
        orderService.importOrder(file);

        return Result.success("导入成功");
    }*/

    /**
     * 导入商品
     * @param file
     * @return
     */
    @PostMapping("/import")
    public Result<String> importOrder(MultipartFile file) throws IOException {
        log.info("导入商品");
        // 使用easyExcel导入商品
        productService.importProduct(file);

        return Result.success("导入成功");
    }

    /**
     * 导出订单模板
     * @param response
     * @return
     */
    @GetMapping("/template")
    public Result<String> exportOrderTemplate(HttpServletResponse response) throws IOException {
        log.info("导出订单模板");
        // 使用easyExcel导出订单模板
        exportTemplate(response);

        return Result.success("导出成功");
    }

    private static void exportTemplate(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("商品导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ProductImportExcelData.class).sheet("模板").doWrite(() -> null);
    }
}
