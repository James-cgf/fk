package com.java.vod.service.impl;

import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.google.common.base.Joiner;
import com.java.vod.service.VodService;
import com.java.vod.util.ConstantVodUtils;
import com.java.vod.util.InitVodCilent;
import com.java.web.exceptionhandler.FkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 曹广福
 * @date 2021/11/14 13:43
 */

@Service
public class VodServiceImpl implements VodService {


    //账号AK信息请填写(必选)
    // @Value("${aliyun.vod.file.keyid}")
    private String accessKeyId = "LTAI5t6Ff8cQtQDtBxoNodkJ";
    //账号AK信息请填写(必选)
    // @Value("aliyun.vod.file.keysecret")
    private String accessKeySecret = "ikJwGMVsEgKSiMmQmC7ZcQ92YgfuGC";

    //上传阿里云视频
    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据id删除阿里云视频
    @Override
    public void removeAlyVideo(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            // String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            //向request设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FkException(20001, "删除视频失败");
        }
    }

    @Override
    public void removeBatchList(List list) {

        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            String ids = Joiner.on(",").join(list);
            System.out.println(ids);

            //向request设置视频id
            request.setVideoIds(ids);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FkException(20001, "删除视频失败");
        }

    }


}
