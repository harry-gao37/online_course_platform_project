package com.course.teacher.vod.service;

import com.course.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-02
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
