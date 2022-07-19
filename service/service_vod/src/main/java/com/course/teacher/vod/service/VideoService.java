package com.course.teacher.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
public interface VideoService extends IService<Video> {


    void removeVideoByCourseId(Long id);

    void removeVideoById(Long id);
}
