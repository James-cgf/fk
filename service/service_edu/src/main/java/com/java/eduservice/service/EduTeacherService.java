package com.java.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-10-25
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

    // List<EduTeacher> getTeacherFrontList(Long page, Long limit);
}
