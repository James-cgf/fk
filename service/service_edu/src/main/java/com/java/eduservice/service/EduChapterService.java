package com.java.eduservice.service;

import com.java.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.eduservice.entity.chapter.ChapterVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据课程id查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //根据章节id查询信息
    List<Map<String, Object>> getChapterInfo(String chapterId);


    //删除章节
    Boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
