package com.course.teacher.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.model.vod.Video;
import com.course.teacher.vod.mapper.VideoMapper;
import com.course.teacher.vod.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.teacher.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    //删除课程  删除所有小节的视频
    @Override
    public void removeVideoByCourseId(Long id) {

        QueryWrapper<Video> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("course_id", id);
        List<Video> videos = baseMapper.selectList(objectQueryWrapper);
        for (Video video : videos) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        }
        baseMapper.delete(objectQueryWrapper);
    }

    //删除小节+删除小节里的视频
    @Override
    public void removeVideoById(Long id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
}
