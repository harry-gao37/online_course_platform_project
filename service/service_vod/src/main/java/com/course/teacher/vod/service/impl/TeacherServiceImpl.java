package com.course.teacher.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.vod.Teacher;
import com.course.teacher.vod.mapper.TeacherMapper;
import com.course.teacher.vod.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-06-29
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Page<Teacher> getTeacherFrontList(Page<Teacher> teacherPage) {
        LambdaQueryWrapper<Teacher> teacherQuery = new LambdaQueryWrapper<>();
        teacherQuery.orderByDesc(Teacher::getId);

        teacherPage = (Page<Teacher>) this.page(teacherPage,teacherQuery);
        return teacherPage;    }
}
