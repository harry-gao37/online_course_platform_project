package com.course.orderFront.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.order.OrderDetail;
import com.course.model.order.OrderInfo;
import com.course.orderFront.mapper.OrderInfoMapper;
import com.course.orderFront.service.OrderDetailService;
import com.course.orderFront.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.vo.order.OrderInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {

        //condition
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();


        //justify whether it is null
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id", userId);
        }
        if (!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status", orderStatus);
        }
        if (!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq("out_trade_no", outTradeNo);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }

        //page query according to the condition
        Page<OrderInfo> orderInfoPage = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = orderInfoPage.getTotal();
        long pageCount = orderInfoPage.getPages();
        List<OrderInfo> records = orderInfoPage.getRecords();

        //query detail by id
        records.stream().forEach(item -> {
            this.getOrderDetail(item);
        });


        //return map
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pageCount);
        map.put("records", records);
        return map;

    }

    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        Long id = orderInfo.getId();
        OrderDetail orderDetail = orderDetailService.getById(id);
        if (orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName", courseName);
        }
        return orderInfo;

    }
}
