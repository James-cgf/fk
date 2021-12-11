package com.java.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.eduservice.entity.EduChapter;
import com.java.eduservice.entity.EduVideo;
import com.java.eduservice.entity.chapter.ChapterVo;
import com.java.eduservice.entity.chapter.VideoVo;
import com.java.eduservice.mapper.EduChapterMapper;
import com.java.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.eduservice.service.EduVideoService;
import com.java.web.exceptionhandler.FkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    EduChapterMapper chapterMapper;

    @Autowired
    EduVideoService eduVideoService;

    //根据课程id获取章节和章节下的小节
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id获取章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //根据课程id查询课程里所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper();
        wrapperChapter.eq("course_id", courseId);
        final List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //创建list集合，用于最后数据封装使用
        List<ChapterVo> resultList = new ArrayList<>();

        //遍历章节list进行数据封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //声明章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            EduChapter chapter = eduChapterList.get(i);
            chapterVo.setId(chapter.getId());
            chapterVo.setTitle(chapter.getTitle());

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //循环遍历小节，将章节id等于小节的章节id放入章节vo对象中
            for (int z = 0; z < eduVideoList.size(); z++) {
                VideoVo videoVo = new VideoVo();
                if (chapter.getId().equals(eduVideoList.get(z).getChapterId())) {
                    videoVo.setId(eduVideoList.get(z).getId());
                    videoVo.setTitle(eduVideoList.get(z).getTitle());
                    videoVo.setVideoSourceId(eduVideoList.get(z).getVideoSourceId());
                    videoList.add(videoVo);
                }
                chapterVo.setChildren(videoList);
            }
            resultList.add(chapterVo);
        }
        return resultList;
    }

    // 根据章节id查询
    @Override
    public List<Map<String, Object>> getChapterInfo(String chapterId) {
        List<Map<String, Object>> list = chapterMapper.getChapterInfo(chapterId);
        return list;
    }

    //根据章节id删除小节
    @Override
    public Boolean deleteChapter(String chapterId) {
        //根据章节id查询小节，有数据不能删除章节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        //根据章节id查询小节数量
        int count = eduVideoService.count(wrapper);
        if (count > 0) {
            throw new FkException(20001, "章节下还有小节，不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }


}
