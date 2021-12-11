package com.java.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduCourse;
import com.java.eduservice.entity.EduTeacher;
import com.java.eduservice.service.EduCourseService;
import com.java.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

/**
 * @author 前端查询控制
 * @date 2021/11/28 21:36
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    EduTeacherService teacherService;

    //注入课程service
    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation("分页查询讲师")
    @PostMapping("getTeacherFrontList")
    public AjaxResult getTeacherFrontList(@RequestParam("page") Long page,@RequestParam("limit") Long limit){
        Page<EduTeacher> pageTeacher=new Page<>(page,limit);
        Map<String,Object> map= teacherService.getTeacherFrontList(pageTeacher);
        return AjaxResult.ok().data(map);
    }

//讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public AjaxResult getTeacherFrontInfo(@PathVariable("teacherId") String teacherId){
        System.out.println(teacherId);
        //根据讲师id查询讲师基本信息
         EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
         List<EduCourse> courseList = eduCourseService.list(wrapper);
        return AjaxResult.ok().data("teacher",eduTeacher).data("courseLisst",courseList);
    }

}
