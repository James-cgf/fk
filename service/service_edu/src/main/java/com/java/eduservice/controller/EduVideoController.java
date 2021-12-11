package com.java.eduservice.controller;


import com.java.commonutils.AjaxResult;
import com.java.eduservice.client.VodClient;
import com.java.eduservice.entity.EduVideo;
import com.java.eduservice.service.EduVideoService;
import com.java.web.exceptionhandler.FkException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器 小节
 * </p>
 *
 * @author 小曹
 * @since 2021-11-05
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/eduVideo")
public class EduVideoController {

    @Autowired
    EduVideoService videoService;


    //调用接口进行远程调用service_vod模块
    @Resource
    VodClient vodClient;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public AjaxResult addVideo(@RequestBody EduVideo eduVideo) {
        boolean save = videoService.save(eduVideo);
        if (save) {
            return AjaxResult.ok();
        }
        return AjaxResult.error();

    }

    //删除小节
    //todo：需要完善：删除小节后需要把里面视频也删除
    @DeleteMapping("{id}")
    @ApiOperation("删除小节")
    public AjaxResult deleteVideo(@PathVariable("id") String id) {

        //根据课程id查询出视频id
        EduVideo eduVideo = videoService.getById(id);
        if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
            //删除视频
             AjaxResult result = vodClient.removeAlyVideo(eduVideo.getVideoSourceId());
             if (result.getCode()==20001){
                 throw new FkException(20001,"删除视频失败");
             }
        }

        videoService.removeById(id);


        return AjaxResult.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    @ApiOperation("修改小节")
    public AjaxResult updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return AjaxResult.ok();
    }

    //    根据小节id查询小节信息
    @ApiOperation("根据小节id查询小节信息")
    @GetMapping("getVideoById/{id}")
    public AjaxResult getVideoById(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return AjaxResult.ok().data("result", eduVideo);
    }

}

