package com.course.orderFront.controller;



import com.commonutils.result.ResultCode;
import com.commonutils.vo.ResultVo;
import com.course.exception.CourseException;
import com.course.orderFront.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/orderFront/pay")
@CrossOrigin
public class PayLogController {
    private final PayLogService payLogService;

    public PayLogController(PayLogService payLogService) {
        this.payLogService = payLogService;
    }

    @ApiOperation("生成微信二维码的接口")
    @GetMapping("/createNative/{orderNo}")
    public ResultVo createWxNativePay(@PathVariable String orderNo){
        //返回的信息包括二维码地址，还需要其他的信息

        Map<String,Object> map = payLogService.createWxNativePay(orderNo);
        return ResultVo.ok().data(map);
    }

    @ApiOperation("查询订单的支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultVo queryPayStatus(@PathVariable String orderNo){
        Optional.ofNullable(orderNo).orElseThrow(()-> CourseException.from(ResultCode.PARAMS_ERROR));
       Map<String,String> map =  payLogService.queryPayStatus(orderNo);

       if(CollectionUtils.isEmpty(map)){
           return ResultVo.error().message("支付出错了");
       }

       //如果返回的map不为空，通过map获取订单内容
       if("SUCCESS".equals(map.get("trade_state"))){
           //添加记录到支付表，更新订单表订单状态
           payLogService.updateOrderStatus(map);
           return ResultVo.ok().message("支付成功");
       }
        return ResultVo.ok().code(25000).message("支付中");
    }

}

