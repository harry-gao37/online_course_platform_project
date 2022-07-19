package com.course.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.ucenter.form.MemberForm;
import com.course.vo.ucenter.UcenterMember;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(MemberForm memberForm);

    void register(MemberForm memberForm);

    Integer countRegisterDay(String day);

    UcenterMember getMemberByOpenid(String openid);
}
