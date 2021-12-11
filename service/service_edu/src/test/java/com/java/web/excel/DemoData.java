package com.java.web.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 实现操作excel实体类
 * @date 2021/11/2 14:44
 */
@Data
public class DemoData {

    //该注解为设置表头名称 学生编号 为 表头标题，index 代表的是 第0行
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}
