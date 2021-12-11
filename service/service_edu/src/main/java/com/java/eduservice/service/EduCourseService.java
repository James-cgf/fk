package com.java.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.eduservice.entity.frontvo.CourseFrontVo;
import com.java.eduservice.entity.frontvo.CourseWebVo;
import com.java.eduservice.entity.vo.CourseInfoVo;
import com.java.eduservice.entity.vo.CoursePageVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加表单数据，课程和课程简介
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //   修改课程和课程简介
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //    根据课程id查询课程基本信息
    List<Map<String, Object>> getPublishCourseInfo(String courseId);

    //条件查询带分页显示
    List<Map<String, Object>> getCoursePageList(CoursePageVo coursePageVo);

    //删除课程方法
    void removeCourse(String courseId);


    //1 条件查询带分页查询课程前台
    List<Map<String, Object>> getCourseFrontList(long page, long limit, CourseFrontVo courseFrontVo);

    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
