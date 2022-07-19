package com.course.teacher.vod.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.model.vod.Course;
import com.course.teacher.vod.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/vod/course")
public class CourseApiController {
    @Autowired
    private  CourseService courseService;

    @GetMapping("inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @PathVariable String keyword){
        QueryWrapper<Course> queryWrapper = new QueryWrapper();
        queryWrapper.like("title", keyword);
        List<Course> list = courseService.list(queryWrapper);
        return list;
    }

}
