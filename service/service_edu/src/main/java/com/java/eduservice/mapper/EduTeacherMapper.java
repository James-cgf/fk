package com.java.eduservice.mapper;

import com.java.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author 小曹
 * @since 2021-10-25
 */
@Repository
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {


//    讲师分页查询
// List<EduTeacher> getTeacherFrontList(Long page, Long limit);


}
