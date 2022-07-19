package com.course.teacher.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.course.model.vod.Subject;
import com.course.teacher.vod.mapper.SubjectMapper;
import com.course.vo.vod.SubjectEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    @Autowired
    private SubjectMapper subjectMapper;
    //一行一行，从第二行
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo,subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
