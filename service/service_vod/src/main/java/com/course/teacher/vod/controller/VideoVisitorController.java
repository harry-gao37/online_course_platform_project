package com.course.teacher.vod.controller;


import com.course.result.Result;
import com.course.teacher.vod.service.VideoVisitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-02
 */
@Api(tags ="Video Visitor Stat Interface" )
@RestController
@RequestMapping("/admin/vod/videoVisitor")
//@CrossOrigin
public class VideoVisitorController {
    @Autowired
    private VideoVisitorService videoVisitorService;


    //课程统计接口
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate){
        Map<String,Object> map = videoVisitorService.findCount(courseId,startDate,endDate);
        return Result.ok(map);
    }

}

