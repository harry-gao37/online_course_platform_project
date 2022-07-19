package com.course.cms.controller;

import com.commonutils.vo.ResultVo;
import com.course.vo.crm.CrmBanner;
import com.course.cms.service.CrmBannerService;
import com.course.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Frontend Banner Interface")
@RestController
@RequestMapping("/cms/crmBannerFront")
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public ResultVo getAllBanner(){
        List<CrmBanner> bannerList = bannerService.selectIndexList();
        return ResultVo.ok().data("list",bannerList);
    }
}
