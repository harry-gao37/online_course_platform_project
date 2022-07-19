package com.course.teacher.vod.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.exception.CourseException;
import com.course.model.vod.Subject;
import com.course.teacher.vod.listener.SubjectListener;
import com.course.teacher.vod.mapper.SubjectMapper;
import com.course.teacher.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.vo.vod.SubjectEeVo;
import com.course.vo.vod.subject.OneSubject;
import com.course.vo.vod.subject.TwoSubject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    //课程分类列表
    //懒加载，每次查询一层数据
    @Override
    public List<Subject> selectSubjectList(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        //iterate subjectList and modify hasChildren value
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            boolean hasChild = this.hasChildren(subjectId);
            subject.setHasChildren(hasChild);

        }

        return subjectList;
    }


    //justify next level has data?
    private boolean hasChildren(Long subjectId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", subjectId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }


    //课程分类导出
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //设置下载信息
            //设置下载文件类型
            response.setContentType("application/vnd.ms-excel");
            //设置头信息
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");


            //查询课程分类表所有数据
            List<Subject> dictList = baseMapper.selectList(null);

            //由于write中的实体类名称不一样，所以需要重新传递数据并进行SubjectEeVo中的字段复制
            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }


            //EasyExcel write method
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(dictVoList);


        } catch (Exception e) {
            throw new CourseException(20001, "导出失败");
        }

    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),
                    SubjectEeVo.class,subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new CourseException(20001,"导入失败");
        }
    }

    @Override
    public List<OneSubject> getAllOneAndTwoSubject() {
        List<Subject> subjects = this.list(null);
        /**
         * 所有的一级分类
         */
        List<OneSubject> oneSubjectList = subjects.stream()
                .filter(subject -> subject.getParentId().equals("0"))
                .map(subject -> {
                    OneSubject oneSubject = new OneSubject();
                    oneSubject.setId(subject.getId());
                    oneSubject.setTitle(subject.getTitle());
                    return oneSubject;
                })
                .collect(Collectors.toList());
        /**
         * 封装一级分类下的二级分类
         */
        oneSubjectList.forEach(oneSubject ->{
            List<TwoSubject> twoSubjectList = subjects.stream()
                    .filter(subject -> subject.getParentId().equals(oneSubject.getId()))
                    .map(subject -> {
                        TwoSubject twoSubject = TwoSubject.builder()
                                .id(subject.getId())
                                .title(subject.getTitle())
                                .build();
                        return twoSubject;
                    })
                    .collect(Collectors.toList());

            oneSubject.setChildren(twoSubjectList);
        });
        return oneSubjectList;
    }
}
