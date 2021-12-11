package com.java.eduservice.service;

import com.java.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.eduservice.entity.subject.SubjectGroup;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    //excel添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    //递归查询课程
    List<SubjectGroup> getSubejtList();

    //根据id获取小节
   List< Map<String,Object>>getSubjectXj(String subjectParentId);
}
