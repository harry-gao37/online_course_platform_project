package com.course.orderFront.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.order.OrderInfo;
import com.course.orderFront.service.OrderInfoService;
import com.course.result.Result;
import com.course.vo.order.OrderInfoQueryVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Api(tags="Order Info Interface")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //订单列表（分页）
    @GetMapping("{page}/{limit}")
    public Result listOrder(@PathVariable Long page,
                            @PathVariable Long limit,
                            OrderInfoQueryVo orderInfoQueryVo){
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        Map<String, Object> map = orderInfoService.selectOrderInfoPage(pageParam,orderInfoQueryVo);
        return Result.ok(map);
    }


}

