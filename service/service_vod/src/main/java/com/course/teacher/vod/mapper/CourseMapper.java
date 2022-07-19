package com.course.teacher.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course.model.vod.Course;
import com.course.vo.vod.CoursePublishVo;
import com.course.vo.vod.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
public interface CourseMapper extends BaseMapper<Course> {

    //需要在xml中进行sql语句编写
    CoursePublishVo selectCoursePublishVoById(Long id);

    CourseWebVo getBaseCourseInfo(String courseId);
}
