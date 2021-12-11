package com.java.eduservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.eduservice.entity.EduCourse;
import com.java.eduservice.entity.EduCourseDescription;
import com.java.eduservice.entity.frontvo.CourseFrontVo;
import com.java.eduservice.entity.frontvo.CourseWebVo;
import com.java.eduservice.entity.vo.CourseInfoVo;
import com.java.eduservice.entity.vo.CoursePageVo;
import com.java.eduservice.mapper.EduCourseMapper;
import com.java.eduservice.service.EduChapterService;
import com.java.eduservice.service.EduCourseDescriptionService;
import com.java.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.eduservice.service.EduVideoService;
import com.java.web.exceptionhandler.FkException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    //注入课程简介service
    @Autowired
    EduCourseDescriptionService edu_course_description;

    @Autowired
    EduCourseMapper EduCourseMapper;

    //注入小节service
    @Autowired
    EduVideoService eduVideoService;

    //注入章节service
    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    //添加课程基本信息方法
    @Override
    @Transactional//添加事务，失败回滚
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        try {
            EduCourse eduCourse = new EduCourse();
            // courseInfoVo对象转换为eduCourse对象   获取用  //向课程简介表添加课程简介这个注释下的方法
            BeanUtils.copyProperties(courseInfoVo, eduCourse);
            eduCourse.setIsDeleted(0);   //设置逻辑删除为0
            int insert = baseMapper.insert(eduCourse);
            if (insert < 1) {
                throw new FkException(20001, "添加课程失败");
            }
            //向课程简介表添加课程简介
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setId(eduCourse.getId());
            eduCourseDescription.setDescription(courseInfoVo.getDescription());
            edu_course_description.save(eduCourseDescription);
            return eduCourse.getId();
        } catch (Exception e) {
            throw new FkException(20001, "添加失败，回滚操作");
        }
    }

    //修改课程基本信息和课程简介
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //课程基本信息对下次那个
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //修改课程基本信息
        final int i = baseMapper.updateById(eduCourse);
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        final boolean b = edu_course_description.updateById(description);
    }

    @Override
    public List<Map<String, Object>> getPublishCourseInfo(String courseId) {
        List<Map<String, Object>> publishCourseInfo = EduCourseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public List<Map<String, Object>> getCoursePageList(CoursePageVo coursePageVo) {
        //column：列名    value：value值  status:状态   page：其实页   limit：每页数据量
        CoursePageVo coursePageVo1 = new CoursePageVo();
        coursePageVo1.setPage((coursePageVo.getPage() - 1) * coursePageVo.limit);
        coursePageVo1.setColumn(coursePageVo.getColumn());
        coursePageVo1.setLimit(coursePageVo.getLimit());
        coursePageVo1.setStatus(coursePageVo.getStatus());
        coursePageVo1.setValue(coursePageVo.getValue());
        System.out.println("---------------------" + coursePageVo1.getPage());
        System.out.println(coursePageVo1);
        List<Map<String, Object>> coursePageList = EduCourseMapper.getCoursePageList(coursePageVo1);
        return coursePageList;
    }

    //删除课程方法
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除课程小节
        eduVideoService.removeVideoByCourseId(courseId);
        System.out.println("执行完毕：removeVideoByCourseId");
        //根据课程id删除课程简介
        eduChapterService.removeChapterByCourseId(courseId);
        System.out.println("执行完毕：removeChapterByCourseId");

        //根据课程id删除课程描述
        System.out.println("根据课程id删除课程描述" + courseId);
        eduCourseDescriptionService.removeById(courseId);
        System.out.println("执行完毕：removeById");

        //根据课程id删除课程
        // int result = baseMapper.deleteById(courseId);

        int result = EduCourseMapper.updateIsDelete(courseId);

        System.out.println("执行完毕：deleteById");

        if (result < 1) {
            new FkException(20001, "删除失败");
        }
    }

    //1 条件查询带分页查询课程
    @Override
    public List<Map<String, Object>> getCourseFrontList(long page, long limit, CourseFrontVo courseFrontVo) {

        List<Map<String, Object>> map = EduCourseMapper.getCourseFrontList(page, limit, courseFrontVo);

        //map返回
        return map;
    }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
