package com.java.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @author 分页条件查询封装类
 * @date 2021/10/26 10:58
 */

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;

    @ApiModelProperty(value = "查询开始时间,", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "结束时间", example = "2019-12-01 10:10:10")
    private String end;

}
