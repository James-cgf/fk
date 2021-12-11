package com.java.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.eduservice.entity.EduSubject;
import com.java.eduservice.entity.excel.SubjectData;
import com.java.eduservice.service.EduSubjectService;
import com.java.web.exceptionhandler.FkException;
import com.java.web.exceptionhandler.GlobalExceptionHandler;
import org.apache.ibatis.jdbc.Null;

/**
 * @author 监听读取excel监听类，在读取excel时候调用
 * @date 2021/11/2 17:28
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectExcelListener当前类不能交给spring管理，所以我们需要自己new出来，不能注入其他对象
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.eduSubjectService = subjectService;
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        System.out.println("这是excel数据");
        System.out.println(subjectData);
        //一行一行读取excel内容
        if (subjectData == null) {
            throw new FkException(20001, "文件数据为空");
        }

        //判断一级分类是否有重复数据  oneSubject为空说明没有数据，则添加
        EduSubject oneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        System.out.println("这是一级分类数据");
        System.out.println(oneSubject);
        //读取二级分类填充到数据库
        //判断二级分类是否有重复
        QueryWrapper<EduSubject> wrapper = new QueryWrapper();
        wrapper.eq("parent_id", oneSubject.getId());
        wrapper.eq("title", subjectData.getTwoSubjectName());
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(oneSubject.getId());
            eduSubjectService.save(twoSubject);
        }
    }

    //判断一级分类是否有重复数据方法
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //读取完成后
    }
}
