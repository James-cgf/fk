package com.java.msmservice.controller;

import com.java.commonutils.AjaxResult;
import com.java.msmservice.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 曹广福
 * @date 2021/11/17 13:17
 */
@Api("发送验证码")
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService smsService;

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("sendSms/{phone}")
    public AjaxResult sendRegisterSms(@PathVariable("phone") String phone) {

        System.out.println("进入sendRegisterSms");

        if (StringUtils.isEmpty(phone)){
            return AjaxResult.error().data("result","请输入手机验证码");
        }

        String code = smsService.sendRegisterSms(phone);


        return AjaxResult.ok().data("code", code);
    }


    //测试redis中是否有该手机号的验证码
    @GetMapping("getRedisByPhone/{phone}")
    public AjaxResult getRedisByPhone(@PathVariable("phone") String phone) {
        System.out.println("进入getRedisByPhone");
        Object code="你叉叉";
        ValueOperations ops = redisTemplate.opsForValue();
         code = ops.get(phone);
        System.out.println(code);

        return AjaxResult.ok().data("code", code);
    }


}
