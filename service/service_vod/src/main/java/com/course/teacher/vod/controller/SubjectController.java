package com.course.teacher.vod.controller;


import com.commonutils.vo.ResultVo;
import com.course.model.vod.Subject;
import com.course.result.Result;
import com.course.teacher.vod.service.SubjectService;
import com.course.vo.vod.SubjectVo;
import com.course.vo.vod.subject.OneSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Api(tags = "Course Subject Interface")
@RestController
@RequestMapping("/admin/vod/subject")
//@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //课程分类列表
    //懒加载，每次查询一层数据
    @ApiOperation("课程分类列表")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Long id){
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.ok(list);
    }

    //课程分类导出
    @ApiOperation("课程分类导出")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    //课程分类导入
    @ApiOperation("课程分类导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.ok(null);
    }

    @ApiOperation("获取所有分类")
    @GetMapping("/findAllSubject")
    public ResultVo findAllSubject(){
        List<OneSubject> allOneAndTwoSubject = subjectService.getAllOneAndTwoSubject();

        return ResultVo.ok().data("items",allOneAndTwoSubject);
    }


}

