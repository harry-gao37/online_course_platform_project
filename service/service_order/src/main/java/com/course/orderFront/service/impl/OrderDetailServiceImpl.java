package com.course.orderFront.service.impl;

import com.course.model.order.OrderDetail;
import com.course.orderFront.mapper.OrderDetailMapper;
import com.course.orderFront.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
