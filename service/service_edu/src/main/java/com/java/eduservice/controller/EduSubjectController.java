package com.java.eduservice.controller;


import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduSubject;
import com.java.eduservice.entity.subject.SubjectGroup;
import com.java.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    @ApiOperation("添加课程分类，获取上传过来的文件，把文件内容读取出来")
    @PostMapping("addSubject")
    public AjaxResult addSubject(MultipartFile file) {
        System.out.println(file);
        //上传过来的excel文件
        subjectService.saveSubject(file, subjectService);
        return AjaxResult.ok();
    }

    @GetMapping("/getAll")
    @ApiOperation("课程分类列表（树形）")
    public AjaxResult getSubejtList() {
        List<SubjectGroup> subejtList = subjectService.getSubejtList();
        return AjaxResult.ok().data("data",subejtList);
    }

}

