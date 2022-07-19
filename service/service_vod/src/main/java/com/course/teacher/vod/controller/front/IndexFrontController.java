package com.course.teacher.vod.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.model.vod.Course;
import com.course.model.vod.Teacher;
import com.course.result.Result;
import com.course.teacher.vod.service.CourseService;
import com.course.teacher.vod.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Front Index Selection Interface")
@RestController
@RequestMapping("/eduservice/indexFront")
public class IndexFrontController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程
    @GetMapping("course")
    public Result course() {
        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> eduList = courseService.list(wrapper);



        return Result.ok(eduList);
    }

    //查询前4条名师
    @GetMapping("teacher")
    public Result teacher() {

        //查询前4条名师
        QueryWrapper<Teacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapperTeacher);

        return Result.ok(teacherList);
    }
}