package com.java.eduservice.mapper;

import com.java.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author 小曹
 * @since 2021-11-02
 */

@Repository
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

    //查询所有课程
    List<EduSubject> getTreeList();

    List<EduSubject> getChildTreeList(String id);

    //根据id查询所有子集
    List<Map<String, Object>> getSubjectXj(String subjectParentId);
}
