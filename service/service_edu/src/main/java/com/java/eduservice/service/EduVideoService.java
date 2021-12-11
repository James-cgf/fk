package com.java.eduservice.service;

import com.java.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除课程小节
    void removeVideoByCourseId(String courseId);
}
