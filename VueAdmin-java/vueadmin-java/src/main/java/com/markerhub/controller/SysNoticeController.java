package com.markerhub.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController extends BaseController {

//    @PreAuthorize("hasAuthority('sys:notice:list')")
    @GetMapping("/list")
    public Result info(String content) {

        //List<SysEvent> sysEvent = sysEventService.list();   //Result(code=200, msg=操作成功, data=[SysEvent(eventname=活动一, num=5, region=校外, location=岳麓区, sort=社区公益服务, date=2023-03-10, time=2023-03-10T11:41:40, duration=3, phone=12345678, leader=计科四支部, remark=活动详情)])

        Page<SysNotice> pageData = sysNoticeService.page(getPage(), new QueryWrapper<SysNotice>()
                .like(StrUtil.isNotBlank(content), "content", content));

//        pageData.getRecords().forEach(u -> {
//
//            u.setSysEvents(sysEventService.listRolesByUserId(u.getId()));
//        });
        System.out.println("公告pageData:" + pageData);//pageData:com.baomidou.mybatisplus.extension.plugins.pagination.Page@5f928d15

        return Result.succ(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:notice:save')")
    public Result save(@Validated @RequestBody SysNotice sysNotice) {

        sysNotice.setCreated(LocalDateTime.now());
        sysNotice.setStatu(Const.STATUS_ON);

        sysNoticeService.save(sysNotice);
        return Result.succ(sysNotice);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:notice:list')")
    public Result info(@PathVariable(name = "id") Long id) {
        return Result.succ(sysNoticeService.getById(id));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Result update(@Validated @RequestBody SysNotice sysNotice) {

        sysNotice.setUpdated(LocalDateTime.now());

        sysNoticeService.updateById(sysNotice);
        return Result.succ(sysNotice);
    }

    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Result delete(@RequestBody Long[] ids) {

        sysNoticeService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }


}
