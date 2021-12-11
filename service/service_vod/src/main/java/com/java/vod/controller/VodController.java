package com.java.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.java.commonutils.AjaxResult;
import com.java.vod.service.VodService;
import com.java.vod.service.impl.VodServiceImpl;
import com.java.vod.util.ConstantVodUtils;
import com.java.vod.util.InitVodCilent;
import com.java.web.exceptionhandler.FkException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author上传视频controoler
 * @date 2021/11/14 13:40
 */

@Api("阿里云视频点播服务")
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    VodService vodService;

    @ApiOperation("阿里云上传视频")
    @PostMapping("uploadAlyiVideo")
    public AjaxResult uploadAlyiVideo(MultipartFile file) {
        String result = vodService.uploadVideoAly(file);
        return AjaxResult.ok().data("videoId", result);
    }

    @ApiOperation("根据阿里云视频id删除阿里云视频")
    @PostMapping("removeAlyVideo")
    public AjaxResult removeAlyVideo(@RequestParam("videoId") String videoId) {
        vodService.removeAlyVideo(videoId);

        return AjaxResult.ok();
    }


    //删除多个阿里云视频
    @ApiOperation("删除多个阿里云视频")
    @PostMapping("deleteBatch")
    public AjaxResult deleteBatch(@RequestParam("list") List<String> list) {

        System.out.println("----------------------------");
        System.out.println(list);

        vodService.removeBatchList(list);
        return AjaxResult.ok();
    }

    //根据视频id获取视频凭证
    //根据凭证播放视频
    @GetMapping("getPlayAuth/{id}")
    public AjaxResult getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return AjaxResult.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new FkException(20001, "获取凭证失败");
        }
    }
}
