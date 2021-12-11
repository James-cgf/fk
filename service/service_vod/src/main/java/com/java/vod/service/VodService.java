package com.java.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 曹广福
 * @date 2021/11/14 13:43
 */
public interface VodService {


    //上传阿里云视频
    String uploadVideoAly(MultipartFile file);

    //根据id删除阿里云视频
    void removeAlyVideo(String videoId);

    //删除多个阿里云视频（list全是id）
    void removeBatchList(List list);
}
