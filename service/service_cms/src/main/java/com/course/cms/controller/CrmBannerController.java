package com.course.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.commonutils.vo.ResultVo;
import com.course.vo.crm.CrmBanner;
import com.course.cms.service.CrmBannerService;
import com.course.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-05
 */
@Api(tags = "Backend Banner Interface")
@RestController
@RequestMapping("/cms/crmBanner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("{page}/{limit}")
    public ResultVo index(
            @PathVariable Long page,
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.pageBanner(pageParam,null);
        return ResultVo.ok().data("item",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CrmBanner banner = bannerService.getBannerById(id);
        return Result.ok(banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public Result save(@RequestBody CrmBanner banner) {
        bannerService.saveBanner(banner);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result update(@RequestBody CrmBanner banner) {
        bannerService.updateBanner(banner);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        bannerService.removeBannerById(id);
        return Result.ok(null);
    }

}

