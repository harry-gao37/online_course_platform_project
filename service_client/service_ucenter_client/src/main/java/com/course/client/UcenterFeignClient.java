package com.course.client;

import com.course.vo.ucenter.MemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter")
public interface UcenterFeignClient {
    /**
     * 根据会员id  查询会员信息
     * @param  id 会员id
     * @return MemberOrderVo 会员信息
     */
    @GetMapping("/ucenter/member/getMemberInfoOrder/{id}")
    MemberOrderVo getMemberInfoOrderById(@PathVariable("id") String id);

    @GetMapping("/ucenter/member/countRegister/{day}")
    Integer countRegister(@PathVariable String day);
}
