package com.course.teacher.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.course.model.vod.Subject;
import com.course.vo.vod.SubjectVo;
import com.course.vo.vod.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-01
 */
public interface SubjectService extends IService<Subject> {

    //查询下一层课程分类
    List<Subject> selectSubjectList(Long id);

    //EasyExcel导出功能
    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);

    List<OneSubject> getAllOneAndTwoSubject();
}
