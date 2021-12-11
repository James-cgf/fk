package com.java.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.educms.entity.CrmBanner;
import com.java.educms.mapper.CrmBannerMapper;
import com.java.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 小曹
 * @since 2021-11-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    CrmBannerMapper mapper;

    //Cacheable:将查询出来的数据放在缓存中，下次查询先判断缓存中是否有数据
    @Cacheable(key = "'banner'",value = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
         //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 2");

         List<CrmBanner> crmBanners = mapper.selectList(queryWrapper);
        return crmBanners;
    }
}
