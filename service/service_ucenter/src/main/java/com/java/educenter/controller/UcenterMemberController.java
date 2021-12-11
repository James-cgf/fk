package com.java.educenter.controller;


import cn.hutool.crypto.SecureUtil;
import com.java.commonutils.AjaxResult;
import com.java.commonutils.JwtUtils;
import com.java.educenter.entity.UcenterMember;
import com.java.educenter.entity.vo.RegisterVo;
import com.java.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表  前端登录注册
 * </p>
 *
 * @author 小曹
 * @since 2021-11-19
 */
@CrossOrigin
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {


    @Autowired
    UcenterMemberService ucenterMemberService;


    //登录
    @PostMapping("login")
    public AjaxResult login(@RequestBody UcenterMember ucenterMember) {

        String token = ucenterMemberService.login(ucenterMember);

        return AjaxResult.ok().data("token", token);
    }

    //注册
    @PostMapping("register")
    public AjaxResult register(@RequestBody RegisterVo registerVo) {
        System.out.println(registerVo);
        boolean result = ucenterMemberService.register(registerVo);
        if (result) {
            return AjaxResult.ok();
        } else {
            return AjaxResult.error();
        }
    }


    //根据token获取用户信息  (从request中获取token)
    @GetMapping("getMemberInfo")
    public AjaxResult getMemberInfo(HttpServletRequest request) {
        //根据request获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
         UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return AjaxResult.ok().data("userInfo",ucenterMember);
    }

}

