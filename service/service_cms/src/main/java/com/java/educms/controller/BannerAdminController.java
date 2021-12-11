package com.java.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.commonutils.AjaxResult;
import com.java.educms.entity.CrmBanner;
import com.java.educms.mapper.CrmBannerMapper;
import com.java.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 小曹
 * @since 2021-11-16
 */

@CrossOrigin
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    CrmBannerService bannerService;

    //    分页查询banner
    @GetMapping("pageBanner")
    @ApiOperation("分页查询banner")
    public AjaxResult pageBanner(@RequestParam("page") Long page, @RequestParam("limit") Long limit) {

        Page<CrmBanner> pageBanner = new Page<CrmBanner>(page, limit);
        bannerService.page(pageBanner, null);

        return AjaxResult.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public AjaxResult pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return AjaxResult.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //2 添加banner
    @PostMapping("addBanner")
    public AjaxResult addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return AjaxResult.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public AjaxResult get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return AjaxResult.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public AjaxResult updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return AjaxResult.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public AjaxResult remove(@PathVariable String id) {
        bannerService.removeById(id);
        return AjaxResult.ok();
    }


}

