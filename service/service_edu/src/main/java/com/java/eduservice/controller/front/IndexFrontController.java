package com.java.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduCourse;
import com.java.eduservice.entity.EduTeacher;
import com.java.eduservice.service.EduCourseService;
import com.java.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author    前端查询controller
 * @date 2021/11/16 14:26
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    //课程service
    @Autowired
    EduCourseService eduCourseService;

    //讲师service
    @Autowired
    EduTeacherService eduTeacherService;

    //查询签八条热门课程，查询前4名名师
    @GetMapping("index")
    @ApiOperation("查询签八条热门课程，查询前4名名师")
    public AjaxResult index() {
        // 查询签八条热门课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
         List<EduCourse> listCourse = eduCourseService.list(wrapperCourse);


        // 查询前4名名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        final List<EduTeacher> listTeacher = eduTeacherService.list(wrapperTeacher);
        return AjaxResult.ok().data("eightCourse", listCourse).data("foure", listTeacher);

    }


}
