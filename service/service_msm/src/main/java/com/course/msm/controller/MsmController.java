package com.course.msm.controller;

import com.commonutils.result.ResultCode;
import com.commonutils.vo.ResultVo;
import com.course.exception.CourseException;
import com.course.msm.service.MsmService;
import com.course.msm.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Api(tags = "Message Service")
@RestController
@RequestMapping("/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;




    //Sending Message Token
    @GetMapping("/send/{phone}")
    public ResultVo sendMsm(@PathVariable String phone){

        //从redis获取验证码，如果能获取的到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isNotBlank(code)){
            return ResultVo.ok().data("code",code);
        }
        //生成随机数，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();

        Map<String,Object> param = new HashMap<>(16);
        param.put("code", code);

        boolean isSend = msmService.send(param,phone);
        if(!isSend){
            throw  CourseException.from(ResultCode.VALID_CODE_SEND_FAIL);
        }
        //发送成功，把发送的验证码放到redis里面
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        return ResultVo.ok().data("code",code);
    }

}
