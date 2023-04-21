package com.markerhub.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.SysEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pc
 * @date 2023/4/17 17:30
 */
@RestController
@RequestMapping("/sys/history")
public class HistoryController extends BaseController{
//    @PreAuthorize("hasAuthority('sys:history:list')")
//    @GetMapping("/list")
//    public Result info(String eventname) {
//
//        //List<SysEvent> sysEvent = sysEventService.list();   //Result(code=200, msg=操作成功, data=[SysEvent(eventname=活动一, num=5, region=校外, location=岳麓区, sort=社区公益服务, date=2023-03-10, time=2023-03-10T11:41:40, duration=3, phone=12345678, leader=计科四支部, remark=活动详情)])
//
//        Page<SysEvent> pageData = sysEventService.page(getPage(), new QueryWrapper<SysEvent>()
//                .like(StrUtil.isNotBlank(eventname), "eventname", eventname));
//        System.out.println("活动pageData:" + pageData);//pageData:com.baomidou.mybatisplus.extension.plugins.pagination.Page@5f928d15
//
//        return Result.succ(pageData);
//    }

    @PreAuthorize("hasAuthority('sys:history:list')")
    @GetMapping("/list")
    public Result info(String eventname) {

        //List<SysEvent> sysEvent = sysEventService.list();   //Result(code=200, msg=操作成功, data=[SysEvent(eventname=活动一, num=5, region=校外, location=岳麓区, sort=社区公益服务, date=2023-03-10, time=2023-03-10T11:41:40, duration=3, phone=12345678, leader=计科四支部, remark=活动详情)])

        Page<SysEvent> pageData = sysEventService.page(getPage(), new QueryWrapper<SysEvent>()
                .like(StrUtil.isNotBlank(eventname), "eventname", eventname));
        System.out.println("活动pageData:" + pageData);//pageData:com.baomidou.mybatisplus.extension.plugins.pagination.Page@5f928d15

        return Result.succ(pageData);
    }
}





