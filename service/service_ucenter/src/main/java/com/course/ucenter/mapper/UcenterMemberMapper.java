package com.course.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course.vo.ucenter.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author YIFU
 * @since 2022-07-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);
}
