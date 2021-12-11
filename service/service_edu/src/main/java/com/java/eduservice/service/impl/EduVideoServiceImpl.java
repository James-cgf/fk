package com.java.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.commonutils.AjaxResult;
import com.java.eduservice.client.VodClient;
import com.java.eduservice.entity.EduVideo;
import com.java.eduservice.mapper.EduVideoMapper;
import com.java.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Resource
    VodClient vodClient;

    //根据课程id删除课程小节
    //todo:删除小节的时候还要删除视频
    @Override
    public void removeVideoByCourseId(String courseId) {

        //根据课程id查询课程所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        List<String> videoIds = new ArrayList<>();
        System.out.println("查询出来的数量："+eduVideoList.size());

        for (EduVideo eduVideo:eduVideoList) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }

        // for (int i = 0; i < eduVideoList.size(); i++) {
        //     EduVideo eduVideo = eduVideoList.get(i);
        //     if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
        //         videoIds.add(eduVideo.getVideoSourceId());
        //     }
        // }
        System.out.println("------------------------------------");
        System.out.println(videoIds);
        AjaxResult ajaxResult = vodClient.deleteBatch(videoIds);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
