package com.java.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduCourse;
import com.java.eduservice.entity.chapter.ChapterVo;
import com.java.eduservice.entity.frontvo.CourseFrontVo;
import com.java.eduservice.entity.frontvo.CourseWebVo;
import com.java.eduservice.entity.vo.CoursePageVo;
import com.java.eduservice.service.EduChapterService;
import com.java.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 曹广福
 * @date 2021/12/6 19:52
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public AjaxResult getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        System.out.println("这是page："+page);
        System.out.println("这是limit："+limit);
        System.out.println(courseFrontVo);
        List<Map<String,Object>> map = courseService.getCourseFrontList(page,limit,courseFrontVo);
        //返回分页所有数据
        return AjaxResult.ok().data("data",map);
    }


    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public AjaxResult getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return AjaxResult.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}
