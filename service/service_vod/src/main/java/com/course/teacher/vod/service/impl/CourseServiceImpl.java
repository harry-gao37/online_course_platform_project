package com.course.teacher.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.vod.Course;
import com.course.model.vod.CourseDescription;
import com.course.model.vod.Subject;
import com.course.model.vod.Teacher;
import com.course.teacher.vod.mapper.CourseMapper;
import com.course.teacher.vod.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.vo.vod.*;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    //点播课程列表
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件字段
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long teacherId = courseQueryVo.getTeacherId();
        //一层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();

        //判断条件为空，封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }

        //调用方法实现条件查询分页
        Page<Course> pages = baseMapper.selectPage(pageParam,wrapper);
        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数

        //每页数据集合
        List<Course> records = pages.getRecords();
        //遍历封装讲师和分类名称
        records.stream().forEach(item -> {
            this.getTeacherOrSubjectName(item);
        });

        //封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);
        return map;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //basic info
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);

        //Description

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //primary key mapping: Allowing both description and course class has the same primary key
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    //获取课程信息
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        //Basic Course Info
        Course course = baseMapper.selectById(id);
        if (course == null){
            return null;
        }
        //Course Description Info
        CourseDescription courseDescription = courseDescriptionService.getById(id);

        //Merging
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        if (courseDescription != null){
            courseFormVo.setDescription(courseDescription.getDescription());
        }

        return courseFormVo;
    }

    //修改课程信息
    @Override
    public void updateCourseById(CourseFormVo courseFormVo) {
        //Modify Basic Course Info
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);


        //Modify Description
        CourseDescription description = courseDescriptionService.getById(course.getId());
        description.setDescription(courseFormVo.getDescription());
        description.setId(course.getId());
        courseDescriptionService.updateById(description);

    }

    //最终发布：获取信息（course+teacher+subject）需要编写sql
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    //Finally Publish
    @Override
    public void publishCourseById(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);

    }

    //删除课程
    @Override
    public void removeCourseId(Long id) {
        //Delete Chapter
        chapterService.removeChapterByCourseId(id);
        //Delete subsection
        videoService.removeVideoByCourseId(id);
        //Delete Basic Info
        baseMapper.deleteById(id);
        //Delete Description
        courseDescriptionService.removeById(id);


    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseQuery courseQuery) {

        LambdaQueryWrapper<Course> courseWrapper = new LambdaQueryWrapper<>();
        //若一级分类不为空
        courseWrapper.eq(!StringUtils.isEmpty(courseQuery.getSubjectParentId()),Course::getSubjectParentId,courseQuery.getSubjectParentId());
        //若二级分类不为空
        courseWrapper.eq(!StringUtils.isEmpty(courseQuery.getSubjectId()),Course::getSubjectId,courseQuery.getSubjectId());

        //关注度不为空
        courseWrapper.orderByDesc(!StringUtils.isEmpty(courseQuery.getBuyCountSort()),Course::getBuyCount);

        //最新不为空
        courseWrapper.orderByDesc(!StringUtils.isEmpty(courseQuery.getGmtCreateSort()),Course::getGmtCreate);

        //价格不为空
        courseWrapper.orderByDesc(!StringUtils.isEmpty(courseQuery.getPriceSort()),Course::getPrice);

        this.page(coursePage, courseWrapper);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        //下一页
        boolean hasNext = coursePage.hasNext();
        //上一页
        boolean hasPrevious = coursePage.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = Maps.newHashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return this.baseMapper.getBaseCourseInfo(courseId);
    }

    private Course getTeacherOrSubjectName(Course course) {
        //查询讲师名称 getParam获得不在原来表中的其他参数
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher != null) {
            course.getParam().put("teacherName",teacher.getName());
        }
        //查询分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }

        return course;

    }
}
