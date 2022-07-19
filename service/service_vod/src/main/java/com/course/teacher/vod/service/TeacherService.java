package com.course.teacher.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.vod.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-06-29
 */
public interface TeacherService extends IService<Teacher> {


    Page<Teacher> getTeacherFrontList(Page<Teacher> teacherPage);
}
