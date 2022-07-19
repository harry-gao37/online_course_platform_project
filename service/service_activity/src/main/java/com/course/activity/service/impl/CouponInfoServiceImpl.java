package com.course.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.activity.mapper.CouponInfoMapper;
import com.course.activity.service.CouponInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.activity.service.CouponUseService;
import com.course.client.user.UserInfoFeignClient;
import com.course.model.activity.CouponInfo;
import com.course.model.activity.CouponUse;
import com.course.model.user.UserInfo;
import com.course.vo.activity.CouponUseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponUseService couponUseService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    //获取已经使用分页列表(根据对象查询）
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        //获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id", couponId);
        }
        if (!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status", couponStatus);
        }
        if (!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time", getTimeBegin);
        }
        if (!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time", getTimeEnd);
        }
        //调用方法查询
        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);
        List<CouponUse> couponUseList = page.getRecords();
        couponUseList.stream().forEach(item -> {
            this.getUserInfoById(item);
        });
        return page;
    }

    //远程调用
    private CouponUse getUserInfoById(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if (!StringUtils.isEmpty(userId)){
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if(userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
        return couponUse;

    }
}
