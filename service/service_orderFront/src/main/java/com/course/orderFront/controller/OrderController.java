package com.course.orderFront.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.commonutils.JwtUtils;
import com.commonutils.vo.ResultVo;
import com.course.model.order.Order;
import com.course.orderFront.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/orderFront")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("生成订单")
    @PostMapping("/createOrder/{courseId}")
    public ResultVo createOrder(@PathVariable String courseId, HttpServletRequest request){

        //创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return ResultVo.ok().data("orderNo",orderNo);
    }

    @ApiOperation("根据订单id查询订单详情")
    @GetMapping("/getOrderInfo/{orderNo}")
    public ResultVo getOrderInfo(@PathVariable String orderNo){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo,orderNo);

        Order order = orderService.getOne(queryWrapper);
        return ResultVo.ok().data("order",order);
    }

    @ApiOperation("根据课程id和用户id查询订单表中订单状态")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){

        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCourseId,courseId);
        queryWrapper.eq(Order::getMemberId,memberId);
        queryWrapper.eq(Order::getStatus,1);

        int count = orderService.count(queryWrapper);
        if(count > 0){
            return true;
        }
        return false;
    }
}

