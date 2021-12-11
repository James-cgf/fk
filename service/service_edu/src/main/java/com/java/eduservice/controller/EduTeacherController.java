package com.java.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.entity.EduTeacher;
import com.java.eduservice.entity.vo.TeacherQuery;
import com.java.eduservice.service.EduTeacherService;
import com.java.web.exceptionhandler.FkException;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 小曹
 * @since 2021-10-25
 */
@ApiModel("讲师控制层")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Resource
    EduTeacherService eduTeacherService;

    //查询讲师所有数据
    @ApiOperation(value = "查询讲师所有数据")
    @GetMapping("findAll")
    public AjaxResult findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return AjaxResult.ok().data("items", list);
    }

    //    逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}")
    public AjaxResult removeTeacher(@ApiParam(value = "这是id", required = true) @PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        if (result) {
            return AjaxResult.ok();
        }
        return AjaxResult.error();
    }

    //分页查询
    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public AjaxResult getTeacher(@PathVariable Long current, @PathVariable Long limit) {

        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, limit);
        //将查询出来数据放入page对象中
        eduTeacherService.page(pageTeacher, null);

        //总记录数
        final long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return AjaxResult.ok().data("total", total).data("item", records);
    }


    //分页待条件查询   使用requestbody注解 必须用post请求
    @ApiOperation("条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public AjaxResult pageTeacherCondition(@PathVariable Long current,
                                           @PathVariable Long limit,
                                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        System.out.println(teacherQuery);
        //声明分页
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //条件查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();

        final String begin = teacherQuery.getBegin();  //开始时间
        final String end = teacherQuery.getEnd();       //结束时间
        final String level = teacherQuery.getLevel();   //等级
        final String name = teacherQuery.getName();       //讲师姓名

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.lt("gmt_modified", end);
        }

        wrapper.orderByDesc("gmt_create");

        //调用分页方法传入到pageTeacher对象中
        eduTeacherService.page(pageTeacher, wrapper);

        //获取总条数
        long total = pageTeacher.getTotal();

        //获取当前查询出来数据
        List<EduTeacher> records = pageTeacher.getRecords();

        return AjaxResult.ok().data("total", total).data("items", records);
    }

    //    讲师添加
    @ApiOperation("讲师添加")
    @PostMapping("addTeacher")
    public AjaxResult save(@RequestBody EduTeacher eduTeacher) {
        System.out.println(eduTeacher);
        final boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return AjaxResult.ok();
        }
        return AjaxResult.error();
    }


    //根据id查询
    @ApiOperation("根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public AjaxResult selectById(@PathVariable String id) {
        // try{
        //     int i = 10 / 0;
        // }catch (Exception e){
        //    throw new FkException(99,"除数不能为0");
        // }
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return AjaxResult.ok().data("teacher", eduTeacher);
    }

    //    讲师修改
    @ApiOperation("修改讲师")
    @PostMapping("updateTeacher")
    public AjaxResult updateTeacher(@RequestBody EduTeacher eduTeacher) {
        System.out.println(eduTeacher);
        boolean result = eduTeacherService.updateById(eduTeacher);
        if (result) {
            return AjaxResult.ok();
        }
        return AjaxResult.error();
    }
}

