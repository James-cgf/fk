package com.java.educenter.service;

import com.java.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.educenter.entity.vo.RegisterVo;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-19
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(UcenterMember ucenterMember);

    //注册
    boolean register(RegisterVo registerVo);

    //根据openid判断
    UcenterMember getOpenIdMember(String openid);
}
