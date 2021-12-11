package com.java.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 前端树形结构封装类
 * @date 2021/11/4 10:03
 */
@Data
public class SubjectGroup {

    //id和label和前端数据相对应
    public String id;   //id
    public String label;    //标题
    //树形递归查询
    public List<SubjectGroup> children=new ArrayList<>();

}
