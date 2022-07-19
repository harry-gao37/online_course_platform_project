package com.course.teacher.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.vod.Chapter;
import com.course.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getNestedTreeList(Long courseId);

    void removeChapterByCourseId(Long id);

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
