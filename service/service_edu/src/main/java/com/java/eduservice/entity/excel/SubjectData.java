package com.java.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author excel添加对应实体类
 * @date 2021/11/2 18:27
 */
@Data
public class SubjectData {

    @ExcelProperty(value = "课程一级分类",index = 0)
    public String oneSubjectName;

    @ExcelProperty(value = "课程二级分类",index = 1)
    public String twoSubjectName;



}
