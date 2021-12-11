package com.java.educms.controller;


import com.java.commonutils.AjaxResult;
import com.java.educms.entity.CrmBanner;
// import com.java.educms.redisUtils.RedisUtils;
import com.java.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;


    @Resource
    RedisTemplate redisTemplate;


    //查询所有banner
    @GetMapping("getAllBanner")
    public AjaxResult getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return AjaxResult.ok().data("list",list);
    }

//    测试redis
//     @GetMapping("test")
//     public AjaxResult testRedis(){
//         redisTemplate.boundValueOps("StringKey").set("StringValue");
//         redisTemplate.boundValueOps("StringKey").set("StringValue",1, TimeUnit.MINUTES);
//
//         return AjaxResult.ok().data("result",(String) redisTemplate.boundValueOps("StringKey").get());
//     }

}

