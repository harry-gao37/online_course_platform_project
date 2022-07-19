package com.course.teacher.vod.mapper;

import com.course.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author YIFU
 * @since 2022-07-02
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(@Param("courseId") Long courseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
}
