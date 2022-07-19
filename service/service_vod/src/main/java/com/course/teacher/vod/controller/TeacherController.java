package com.course.teacher.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.model.vod.Teacher;
import com.course.vo.vod.TeacherQueryVo;
import com.course.result.Result;
import com.course.teacher.vod.service.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-06-29
 */
@Api(tags = "Managing Teachers Interface")
@RestController
@RequestMapping("/admin/vod/teacher")
//@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    //查询所有讲师
    @ApiOperation("Finding all teachers")
    @GetMapping("findAll")
    public Result findAllTeacher() {

        List<Teacher> list = null;
        try {
            list = teacherService.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(list).data(list);

    }

    @ApiOperation("Logic deleting teacher by id")
    @DeleteMapping("remove/{id}")
    public Result removeTeacherById(@ApiParam(name = "id", value = "ID", required = true) @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }


    /*条件查询分页*/
    @ApiOperation("Conditional query pagination")
    @PostMapping("findQueryPage/{page}/{limit}") //used with RequestBody to get parameters in JSON form
    public Result findPage(@PathVariable long page,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> pageParam = new Page<>(page, limit);

        if (teacherQueryVo == null) {
            IPage<Teacher> pageModel = teacherService.page(pageParam, null);

            return Result.ok(pageModel);
        } else {
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date", joinDateEnd);
            }

            IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);

            return Result.ok(pageModel);
        }
    }


    /* Adding new teachers*/
    @ApiOperation("Adding new teachers")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }


    /* Modifying by ID */
    @ApiOperation(" Finding teacher by ID")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }


    @ApiOperation(" Updating Teacher")
    @PostMapping("updateTeacher")
    public Result updateaTeacher(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok(null);
    }

    /* Batch deletion */
    // Json Array form using list, Data will come from front end
    @ApiOperation("Deleting teachers By ID")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        if (isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }



}

