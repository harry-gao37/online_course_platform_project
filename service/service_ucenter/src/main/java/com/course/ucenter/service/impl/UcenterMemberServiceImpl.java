package com.course.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.commonutils.JwtUtils;
import com.commonutils.MD5;
import com.commonutils.result.ResultCode;
import com.course.exception.CourseException;
import com.course.model.ucenter.form.MemberForm;
import com.course.ucenter.mapper.UcenterMemberMapper;
import com.course.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.vo.ucenter.UcenterMember;
import com.course.vo.vod.CoursePublishVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    @Override
    public String login(MemberForm memberForm) {
        //获取手机和密码
        String mobile = memberForm.getMobile();
        String password = memberForm.getPassword();

        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)){
            throw CourseException.from(ResultCode.LOGIN_ERROR);
        }


        //判断手机号是否正确
        LambdaQueryWrapper<UcenterMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(UcenterMember::getMobile,mobile);

        UcenterMember member = this.getOne(memberQuery);

        //判断对象是否为空
        member = Optional.ofNullable(member).orElseThrow(() -> CourseException.from(ResultCode.LOGIN_ERROR));


        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equalsIgnoreCase(member.getPassword())){
            throw CourseException.from(ResultCode.LOGIN_ERROR);
        }

        //判断用户是否禁用
        if (member.getIsDisabled()){
            throw CourseException.from(ResultCode.LOGIN_ERROR);
        }

        //登录成功
        //生成token字符串，使用jwt字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    @Override
    public void register(MemberForm memberForm) {
        //获取注册的数据

        String mobile = memberForm.getMobile();
        String password = memberForm.getPassword();
        String code = memberForm.getCode();
        String nickname = memberForm.getNickname();

        //非空判断
        if (   StringUtils.isBlank(mobile)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(code)
                || StringUtils.isBlank(nickname)
        ){
            throw CourseException.from(ResultCode.REGISTER_ERROR);
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equalsIgnoreCase(redisCode)){
            throw CourseException.from(ResultCode.REGISTER_ERROR);
        }

        //判断手机号是否重复，表里面是否存在相同手机号不进行添加
        LambdaQueryWrapper<UcenterMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(UcenterMember::getMobile,mobile);

        int count = this.count(memberQuery);
        if (count > 0){
            throw CourseException.from(ResultCode.REGISTER_ERROR);
        }

        //数据添加到数据库
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(memberForm,member);
        //密码需要做加密处理
        member.setPassword(member.getPassword());
        this.save(member);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return this.baseMapper.countRegisterDay(day);
    }

    @Override
    public UcenterMember getMemberByOpenid(String openid) {
        return null;
    }
}
