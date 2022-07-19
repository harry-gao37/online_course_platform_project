package com.course.vo.vod.Front;

import com.course.vo.vod.VideoVo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;



@Data
@AllArgsConstructor
public class ChapterFrontVo {

    private String id;

    private String title;


    /**
     * 小节
     */
    private List<VideoVo> children = Lists.newArrayList();

    public ChapterFrontVo(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
