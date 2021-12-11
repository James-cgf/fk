package com.java.eduservice.controller;

import com.java.commonutils.AjaxResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author 曹广福
 * @date 2021/10/29 13:10
 */
@Api("登录controller")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("/login")
    public AjaxResult login(){
        return AjaxResult.ok().data("token","admin");


    }
    @GetMapping("info")
    public AjaxResult info(){
        return AjaxResult.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
