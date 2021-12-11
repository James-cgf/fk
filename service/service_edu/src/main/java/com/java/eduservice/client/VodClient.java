package com.java.eduservice.client;

import com.java.commonutils.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 调用service_vod模块下的接口
 * @date 2021/11/15 13:28
 */

@Component    //交给spring管理
//fallback = VodFileDegradeFeignClient.class  出错时候执行实现接口
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)    //要调用的服务名
public interface VodClient {

    //调用service_vod模块下删除视频的路径
    //PathVariable后面一定要跟路径参数的名称，否则会报错
    // @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    // public AjaxResult removeAlyVideo(@PathVariable("id") String id);
    @PostMapping("/eduvod/video/removeAlyVideo")
    //复制这个接口的完全路径
    AjaxResult removeAlyVideo(@RequestParam("videoId") String videoId);

    // //删除多个视频    注意这里有坑，list必须加String类型，否则会报bean找不到
    @PostMapping("/eduvod/video/deleteBatch")
    AjaxResult deleteBatch(@RequestParam("list") List<String> list);
}
