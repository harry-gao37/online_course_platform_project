package com.course.teacher.vod.service.impl;

import com.course.model.vod.VideoVisitor;
import com.course.teacher.vod.mapper.VideoVisitorMapper;
import com.course.teacher.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.vo.vod.VideoVisitorCountVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-02
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo>  videoVisitorCountVoList= baseMapper.findCount(courseId,startDate,endDate);
        HashMap<String, Object> map = new HashMap<>();
        //创建两个list集合，一个代表所有日期，一个代表日期对应数量

        List<String> dateList = videoVisitorCountVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        List<Integer> countList = videoVisitorCountVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());
        //放到map集合
        map.put("xData", dateList);
        map.put("yData", countList);
        return map;
    }
}
