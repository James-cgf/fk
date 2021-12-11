package com.java.eduservice.controller;


import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduChapter;
import com.java.eduservice.entity.chapter.ChapterVo;
import com.java.eduservice.service.EduChapterService;
import com.java.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduSubjectService subjectService;

    //获取大纲列表，根据课程id进行查询
    @ApiOperation("根据id查询课程大纲")
    @GetMapping("getList/{courseId}")
    public AjaxResult getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return AjaxResult.ok().data("list", list);
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    @ApiOperation("根据章节id查询")
    public AjaxResult getChapterInfo(@PathVariable String chapterId) {
        List<Map<String, Object>> list = chapterService.getChapterInfo(chapterId);
        System.out.println("老纸是章节id：" + list.get(0).get("subject_id"));
        List<Map<String, Object>> subjectXj = subjectService.getSubjectXj(list.get(0).get("subject_parent_id").toString());
        return AjaxResult.ok().data("chapter", list).data("chirend", subjectXj);
    }

    //  添加章节
    @PostMapping("addChapter")
    @ApiOperation("添加章节")
    public AjaxResult addChapter(@RequestBody EduChapter chapter) {
        boolean save = chapterService.save(chapter);
        if (save) {
            return AjaxResult.ok().data("result", "操作成功");
        } else {
            return AjaxResult.error().data("result", "操作失败");
        }
    }

    //    根据章节id查询
    @GetMapping("getChapterInfoById/{chapterId}")
    @ApiOperation("根据章节id查询")
    public AjaxResult getChapterInfoById(@PathVariable String chapterId) {
        final EduChapter chapter = chapterService.getById(chapterId);
        return AjaxResult.ok().data("chapter", chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    @ApiOperation("修改章节")
    public AjaxResult updateChapter(@RequestBody EduChapter chapter) {
        final boolean b = chapterService.updateById(chapter);
        return AjaxResult.ok().data("result", "操作成功");
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    @ApiOperation("删除章节")
    public AjaxResult deleteChapter(@PathVariable String chapterId) {
        System.out.println(chapterId);
        Boolean result = chapterService.deleteChapter(chapterId);
        if (result) {
            return AjaxResult.ok().data("result", "删除成功");
        }
            return AjaxResult.error().data("result", "删除失败");
    }
}

