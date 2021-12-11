package com.java.eduservice.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.client.VodClient;
import com.java.eduservice.entity.EduCourse;
import com.java.eduservice.entity.vo.CourseInfoVo;
import com.java.eduservice.entity.vo.CoursePageVo;
import com.java.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Api("添加课程类Api")
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    //注入远程服务接口
    VodClient vodClient;

    @ApiOperation("添加课程")
    @PostMapping("addCourseInfo")
    public AjaxResult addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String id = courseService.saveCourseInfo(courseInfoVo);
        return AjaxResult.ok().data("courseId", id);
    }


    @ApiOperation("修改课程")
    @PostMapping("updateCourseInfo")
    public AjaxResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return AjaxResult.ok().data("result", "操作成功");
    }

    //    根据课程id查询课程基本信息
    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public AjaxResult getPublishCourseInfo(@PathVariable("courseId") String courseId) {
        List<Map<String, Object>> publishCourseInfo = courseService.getPublishCourseInfo(courseId);
        return AjaxResult.ok().data("publishCourse", publishCourseInfo);

    }

    //发布课程
    @ApiOperation("发布课程(修改状态)")
    @PostMapping("publishCourse/{id}")
    public AjaxResult publishCourse(@PathVariable("id") String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");   //设置课程状态为发布
        boolean result = courseService.updateById(course);
        System.out.println(course);
        if (result) {
            return AjaxResult.ok();
        }
        return AjaxResult.error();
    }


    //    条件查询带分页
    @ApiOperation("条件查询带分页显示")
    @PostMapping("getCoursePageList")
    public AjaxResult getCoursePageList(@RequestBody CoursePageVo coursePageVo) {

        List<Map<String, Object>> coursePageList = courseService.getCoursePageList(coursePageVo);

        // 查询总记录数
        final List<EduCourse> list = courseService.list(null);

        return AjaxResult.ok().data("result", coursePageList).data("total", list.size());
    }

    //根据课程删除课程
    @PostMapping("deleteCourse")
    @ApiOperation("删除课程")
    public AjaxResult removeCourse(@RequestParam("courseId") String courseId) {
        System.out.println("这是参数："+courseId);
        courseService.removeCourse(courseId);
        return AjaxResult.ok();

    }

}

