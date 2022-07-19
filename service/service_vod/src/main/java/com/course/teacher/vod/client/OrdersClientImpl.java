package com.course.teacher.vod.client;


import com.commonutils.result.ResultCode;
import com.course.exception.CourseException;
import org.springframework.stereotype.Component;


@Component
public class OrdersClientImpl implements OrdersClient {
    /**
     * 根据课程id和用户id查询订单表中订单状态
     *
     * @param courseId 课程id
     * @param memberId 会员id
     * @return 是否购买
     */
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        throw CourseException.from(ResultCode.ERROR);
    }
}
