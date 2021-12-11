package com.java.educenter.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.commonutils.JwtUtils;
import com.java.commonutils.MD5;
import com.java.educenter.entity.UcenterMember;
import com.java.educenter.entity.vo.RegisterVo;
import com.java.educenter.mapper.UcenterMemberMapper;
import com.java.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.web.exceptionhandler.FkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-19
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    //登录
    @Override
    public String login(UcenterMember ucenterMember) {

        //判断手机号和密码是否为空
        if (StringUtils.isEmpty(ucenterMember.getMobile()) || StringUtils.isEmpty(ucenterMember.getPassword())) {
            throw new FkException(20001, "手机号或密码为空！");
        }

        //判断手机号是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", ucenterMember.getMobile());
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        //判断密码是否正确
        if (!SecureUtil.md5(ucenterMember.getPassword()).equals(mobileMember.getPassword())) {
            throw new FkException(20001, "密码不正确");
        }

        final String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    @Override
    public boolean register(RegisterVo registerVo) {

        //判断昵称，手机号，密码，验证码是否为空
        if (StringUtils.isEmpty(registerVo.getNickname()) || StringUtils.isEmpty(registerVo.getMobile()) || StringUtils.isEmpty(registerVo.getPassword()) || StringUtils.isEmpty(registerVo.getCode())) {
            throw new FkException(20001, "请输入完整数据!");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", registerVo.getMobile());
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (!StringUtils.isEmpty(ucenterMember)) {
            throw new FkException(20001, "手机号已存在！");
        }

        System.out.println("验证代是："+redisTemplate.opsForValue().get(registerVo.getMobile()));
        System.out.println("输入验证："+registerVo.getCode());
        String redisCode=redisTemplate.opsForValue().get(registerVo.getMobile());
        //从redis中拿到验证码，对输入的验证码进行对比
        if (!registerVo.getCode().equals(redisCode)) {
            throw new FkException(20001, "验证码不正确!");
        }

        UcenterMember ucenterMemberEntity = new UcenterMember();
        ucenterMemberEntity.setMobile(registerVo.getMobile());
        ucenterMemberEntity.setNickname(registerVo.getNickname());
        ucenterMemberEntity.setPassword(SecureUtil.md5(registerVo.getPassword()));
        // ucenterMemberEntity.setAvatar("");
        int insert = baseMapper.insert(ucenterMemberEntity);
        if (insert < 1) {
            throw new FkException(20001, "注册失败，请联系管理员");
        }

        return true;
    }

    //根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }


}
