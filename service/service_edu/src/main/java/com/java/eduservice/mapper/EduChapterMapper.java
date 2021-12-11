package com.java.eduservice.mapper;

import com.java.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {

    List<Map<String,Object>> getChapterInfo(String chapterId);
}
