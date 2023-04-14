package com.markerhub.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.SysEvent;
import com.markerhub.entity.SysNotice;
import com.markerhub.entity.SysUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-10
 */
@RestController
@RequestMapping("/sys/event")
public class SysEventController extends BaseController {

    @PreAuthorize("hasAuthority('sys:event:list')")
    @GetMapping("/list")
    public Result info(String eventname) {

        //List<SysEvent> sysEvent = sysEventService.list();   //Result(code=200, msg=操作成功, data=[SysEvent(eventname=活动一, num=5, region=校外, location=岳麓区, sort=社区公益服务, date=2023-03-10, time=2023-03-10T11:41:40, duration=3, phone=12345678, leader=计科四支部, remark=活动详情)])

        Page<SysEvent> pageData = sysEventService.page(getPage(), new QueryWrapper<SysEvent>()
                .like(StrUtil.isNotBlank(eventname), "eventname", eventname));
        System.out.println("活动pageData:"+pageData);//pageData:com.baomidou.mybatisplus.extension.plugins.pagination.Page@5f928d15

        return Result.succ(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:event:save')")
    public Result save(@Validated @RequestBody SysEvent sysEvent) {

        sysEvent.setCreated(LocalDateTime.now());
        sysEvent.setStatu(Const.STATUS_ON);

        sysEventService.save(sysEvent);
        return Result.succ(sysEvent);
    }

}


