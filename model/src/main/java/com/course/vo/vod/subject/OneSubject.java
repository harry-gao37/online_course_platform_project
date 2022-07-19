package com.course.vo.vod.subject;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;



@Data
public class OneSubject {

    private Long id;

    private String title;

    /**
     * 一个一级分类里面有多个二级分类
     */
    List<TwoSubject> children = Lists.newArrayList();
}
