package com.course.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.activity.CouponInfo;
import com.course.model.activity.CouponUse;
import com.course.vo.activity.CouponUseQueryVo;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}
