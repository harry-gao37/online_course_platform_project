package com.course.teacher.vod.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.commonutils.vo.ResultVo;
import com.course.model.vod.Course;
import com.course.model.vod.Teacher;
import com.course.teacher.vod.service.CourseService;
import com.course.teacher.vod.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/edu/teacherFront")
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;


    @ApiOperation("讲师C端分页查询")
    @GetMapping("/getTeacherInfoList/{page}/{size}")
    public ResultVo getTeacherFrontList(@PathVariable long page , @PathVariable long size){

        Page<Teacher> teacherPage = new Page<>(page,size);
        teacherPage = teacherService.getTeacherFrontList(teacherPage);

        return ResultVo.ok().data("page",teacherPage);
    }

    @ApiOperation("讲师详情的功能")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public ResultVo getTeacherFront(@PathVariable String teacherId){

        //1.根据讲师id查询讲师的基本信息
        Teacher teacher = teacherService.getById(teacherId);

        //2.根据讲师查询所讲课程
        LambdaQueryWrapper<Course> courseQuery = new LambdaQueryWrapper<>();
        courseQuery.eq(Course::getTeacherId,teacherId);
        List<Course> courseList = courseService.list(courseQuery);
        return ResultVo.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
