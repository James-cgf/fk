package com.java.eduservice.client;

import com.java.commonutils.AjaxResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 调用service_vod模块下接口，Hystrix实现熔断降级限流操作
 * hystrix
 * @date 2021/11/15 18:58
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    //删除课程和小节时调用，如果service_vod挂了就会执行当前接口下的方法
    @Override
    public AjaxResult removeAlyVideo(String videoId) {

        System.out.println("删除视频出错，执行Hystrix");

        return AjaxResult.error().message("删除视频出错，执行Hystrix");
    }

    @Override
    public AjaxResult deleteBatch(List<String> list) {
        System.out.println("删除多个视频出错，执行Hystrix");
        return AjaxResult.error().message("删除多个视频出错，执行Hystrix");
    }
}
