package com.course.teacher.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.vod.Course;
import com.course.vo.vod.*;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourseById(CourseFormVo courseFormVo);

    CoursePublishVo getCoursePublishVo(Long id);

    void publishCourseById(Long id);

    void removeCourseId(Long id);

    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseQuery courseQuery);

    CourseWebVo getBaseCourseInfo(String courseId);
}
