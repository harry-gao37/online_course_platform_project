package com.course.orderFront.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.course.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);
}
