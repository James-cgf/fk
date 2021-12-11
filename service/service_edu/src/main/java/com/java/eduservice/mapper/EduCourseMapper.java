package com.java.eduservice.mapper;

import com.java.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.eduservice.entity.frontvo.CourseFrontVo;
import com.java.eduservice.entity.frontvo.CourseWebVo;
import com.java.eduservice.entity.vo.CoursePageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程基本信息
    public List<Map<String, Object>> getPublishCourseInfo(@Param("courseId") String courseId);

    //条件查询带分页
    //column：列名    value：value值  status:状态   page：其实页   limit：每页数据量

    List<Map<String, Object>> getCoursePageList(CoursePageVo coursePageVo);

    int updateIsDelete(String courseId);

    //1 条件查询带分页查询课程
    List<Map<String, Object>> getCourseFrontList(@Param("page") long page, @Param("limit") long limit, @Param("courseFrontVo")CourseFrontVo courseFrontVo);

    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(@Param("courseId") String courseId);
}
