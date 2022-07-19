package com.course.teacher.vod.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.vod.Course;
import com.course.result.Result;
import com.course.teacher.vod.service.CourseService;
import com.course.vo.vod.CourseFormVo;
import com.course.vo.vod.CoursePublishVo;
import com.course.vo.vod.CourseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Api(tags = "Course Information Interface")
@RestController
@RequestMapping("/admin/vod/course")
//@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    //点播课程列表
    @ApiOperation("点播课程列表")
    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPageCourse(pageParam, courseQueryVo);

        return Result.ok(map);
    }

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {

        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);

    }

    //根据id获取课程信息
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }

    //修改课程信息
    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseById(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }


    //最终发布：获得信息 + 确认信息
    //根据课程id查询发布课程信息
    @ApiOperation("根据id获取课程发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVoById(
            @PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);

    }

    //课程发布
    @ApiOperation("Finally Publish")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id) {

        courseService.publishCourseById(id);
        return Result.ok(null);

    }

    //删除课程
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        courseService.removeCourseId(id);
        return Result.ok(null);
    }



}

