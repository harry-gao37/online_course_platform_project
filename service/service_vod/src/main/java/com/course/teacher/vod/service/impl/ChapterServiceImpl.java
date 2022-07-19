package com.course.teacher.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.model.vod.Chapter;
import com.course.model.vod.Video;
import com.course.teacher.vod.mapper.ChapterMapper;
import com.course.teacher.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.teacher.vod.service.VideoService;
import com.course.vo.vod.ChapterVo;
import com.course.vo.vod.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;


    //大纲列表
    @Override
    public List<ChapterVo> getNestedTreeList(Long courseId) {
        //定义最终数据list集合
        List<ChapterVo> finalChapterList = new ArrayList<>();
        //获取章节
        QueryWrapper<Chapter> ChapterWrapper = new QueryWrapper<>();
        ChapterWrapper.eq("course_id", courseId);
        List<Chapter> chapters = baseMapper.selectList(ChapterWrapper);

        //获取小节
        LambdaQueryWrapper<Video> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(wrapperVideo);


        //封装章节
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalChapterList.add(chapterVo);

            //封装小节
            //再创建list集合
            List<VideoVo> videoVos = new ArrayList<>();
            for (Video video : videoList) {
                //判断
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            //将小节放入章节中
            chapterVo.setChildren(videoVos);
        }


        return finalChapterList;
    }

    //Delete Chapter
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        baseMapper.delete(queryWrapper);


    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1.根据课程id 查询课程里面所有的章节
        LambdaQueryWrapper<Chapter> chapterQuery = new LambdaQueryWrapper<>();
        chapterQuery.eq(Chapter::getId, courseId);
        List<Chapter> chapterList = this.list(chapterQuery);



        //封装小节
        List<ChapterVo> chapterVoList = chapterList.stream()
                .map(chapter -> new ChapterVo(chapter.getId(), chapter.getTitle()))
                .collect(toList());


        //2.根据课程id查询课程里面所有的小节
        LambdaQueryWrapper<Video> videoQuery = new LambdaQueryWrapper();
        videoQuery.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(videoQuery);
        //封装小节
        chapterVoList.forEach(chapterVo -> {

            List<VideoVo> videoVoList = videoList.stream()
                    .filter(video -> video.getChapterId().equals(chapterVo.getId()))
                    .map(video -> new VideoVo(video.getId(), video.getTitle(), video.getVideoSourceId()))
                    .collect(toList());

            chapterVo.setChildren(videoVoList);
        });

        return chapterVoList;
    }
}
