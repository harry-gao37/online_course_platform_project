package com.course.teacher.vod.controller;


import com.course.model.vod.Chapter;
import com.course.result.Result;
import com.course.teacher.vod.service.ChapterService;
import com.course.vo.vod.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Api(tags = "Chapter Info Interface")
@RestController
@RequestMapping("/admin/vod/chapter")
//@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //大纲列表（章节和小节列表）
    @ApiOperation("嵌套章节数据列表")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getNestedTreeList(
            @PathVariable Long courseId
    ) {
        List<ChapterVo> list = chapterService.getNestedTreeList(courseId);
        return Result.ok(list);

    }

    //添加章节
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok(null);
    }

    //修改-根据id查询
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    //修改-最终实现
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

    //删除章节
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok(null);

    }


}

