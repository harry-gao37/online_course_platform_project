package com.course.orderFront.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.client.UcenterFeignClient;
import com.course.client.VodFeignClient;
import com.course.model.order.Order;
import com.course.orderFront.mapper.OrderMapper;
import com.course.orderFront.service.OrderService;
import com.course.orderFront.utils.OrderNoUtil;
import com.course.vo.ucenter.MemberOrderVo;
import com.course.vo.vod.CourseOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private VodFeignClient vodFeignClient;

    @Autowired
    private UcenterFeignClient ucenterFeignClient;


    /**
     * 创建订单
     *
     * @param courseId 课程号
     * @param memberId 会员Id
     * @return 订单号
     */
    @Override
    public String createOrder(String courseId, String memberId) {

        //通过远程调用根据会员id获取会员信息
        MemberOrderVo memberOrderVo = ucenterFeignClient.getMemberInfoOrderById(memberId);

        //通过远程调用根据课程id获取课程信息
        CourseOrderVo courseInfoOrder = vodFeignClient.getCourseInfoOrder(courseId);

        //创建order 对象 向order对象填充数据

        Order order = Order.builder()
                .orderNo(OrderNoUtil.getOrderNo())
                .courseId(courseId)
                .courseCover(courseInfoOrder.getCover())
                .courseTitle(courseInfoOrder.getTitle())
                .memberId(memberId)
                .nickname(memberOrderVo.getNickname())
                .mobile(memberOrderVo.getMobile())
                .teacherName(courseInfoOrder.getTeacherName())
                .totalFee(courseInfoOrder.getPrice())
                //订单状态 （0：未支付，1：已支付）
                .status(0)
                //支付方式 1.微信
                .payType(1)
                .build();

        this.save(order);
        return order.getOrderNo();
    }
}
