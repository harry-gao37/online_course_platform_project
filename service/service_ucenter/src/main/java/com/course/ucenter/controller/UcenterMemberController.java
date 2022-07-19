package com.course.ucenter.controller;


import com.commonutils.JwtUtils;
import com.commonutils.vo.ResultVo;
import com.course.model.ucenter.form.MemberForm;
import com.course.ucenter.service.UcenterMemberService;
import com.course.vo.ucenter.MemberOrderVo;
import com.course.vo.ucenter.UcenterMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-06
 */
@Api(tags = "Login & Registration Interface")
@RestController
@RequestMapping("/ucenter/ucenterMember")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    @PostMapping("/login")
    @ApiOperation("会员登录")
    public ResultVo login(@RequestBody MemberForm memberForm){
        //memberForm 对象封装手机号和密码

        //返回token 值,使用jwt生成
        String token = ucenterMemberService.login(memberForm);

        return ResultVo.ok().data("token",token);
    }


    @PostMapping("/register")
    @ApiOperation("会员注册")
    public ResultVo register(@RequestBody MemberForm memberForm){
        ucenterMemberService.register(memberForm);
        return ResultVo.ok();
    }

    /**
     * 获取会员信息
     *
     */
    @ApiOperation("获取会员信息")
    @GetMapping("/getMemberInfo")
    public ResultVo getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据Request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //根据数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return ResultVo.ok().data("member",member);
    }

    @ApiOperation("根据会员id获取用户信息")
    @GetMapping("/getMemberInfoOrder/{id}")
    public MemberOrderVo getMemberInfoOrderById(@PathVariable String id){
        UcenterMember member = ucenterMemberService.getById(id);
        MemberOrderVo memberOrderVo = new MemberOrderVo();
        BeanUtils.copyProperties(member,memberOrderVo);
        return memberOrderVo;
    }

    @GetMapping("/countRegister/{day}")
    public Integer countRegister(@PathVariable String day){
        Integer count =  ucenterMemberService.countRegisterDay(day);
        return count;
    }



}

