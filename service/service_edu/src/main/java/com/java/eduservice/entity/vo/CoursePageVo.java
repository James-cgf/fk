package com.java.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 分页待条件查询
 * @date 2021/11/13 14:52
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePageVo {

    public String column; //：列名
    public String value; //   ：要查询的值
    public String status; //       :状态
    public Integer page; //       ：其实页
    public Integer limit; //   ：每页数据量

}
