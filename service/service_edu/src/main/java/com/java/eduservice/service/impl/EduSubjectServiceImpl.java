package com.java.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.eduservice.entity.EduSubject;
import com.java.eduservice.entity.excel.SubjectData;
import com.java.eduservice.entity.subject.SubjectGroup;
import com.java.eduservice.listener.SubjectExcelListener;
import com.java.eduservice.mapper.EduSubjectMapper;
import com.java.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    EduSubjectMapper subjectMapper;

    //excel文件上传添加课程分类   在listener.SubjectExcelListener类下做数据库添加

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            //    调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //课程树状结构
    @Override
    public List<SubjectGroup> getSubejtList() {
        //查询所有分类
        List<EduSubject> eduSubjectList = subjectMapper.getTreeList();
        //定义一级分类List
        List<SubjectGroup> oneSubjectList = new ArrayList<>();
        //找到所有一级分类
        for (EduSubject eduSubject : eduSubjectList) {
            if (eduSubject.getParentId().equals("0")) {
                SubjectGroup subjectGroup = new SubjectGroup();
                subjectGroup.setId(eduSubject.getId());
                subjectGroup.setLabel(eduSubject.getTitle());
                oneSubjectList.add(subjectGroup);
            }
        }
        //为一级分类设置子菜单
        for (SubjectGroup subjectGroup : oneSubjectList) {
            subjectGroup.setChildren(getChildeList(subjectGroup.getId(), eduSubjectList));
        }
        return oneSubjectList;
    }


    //获取传入list里子级菜单
    public List<SubjectGroup> getChildeList(String id, List<EduSubject> parentList) {
        //定义子级菜单list
        List<SubjectGroup> childList = new ArrayList<>();
        for (EduSubject eduSubject : parentList) {
            //遍历节点，将父级id与传过来的id相比较
            if (eduSubject.getParentId().equals(id)) {
                SubjectGroup subjectGroup = new SubjectGroup();
                subjectGroup.setId(eduSubject.getId());
                subjectGroup.setLabel(eduSubject.getTitle());
                childList.add(subjectGroup);
            }
        }
        //再次遍历子菜单
        for (SubjectGroup eduSubject : childList) {
            eduSubject.setChildren(getChildeList(eduSubject.getId(), parentList));
        }
        System.out.println(childList);
        return childList;
    }

    @Override
    public  List<Map<String, Object>> getSubjectXj(String subjectParentId) {
         List<Map<String, Object>> subjectXj = subjectMapper.getSubjectXj(subjectParentId);
        return subjectXj;
    }
}
