package com.markerhub.controller;

import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.ExpressInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author pc
 * @date 2023/4/11 13:49
 */

@RestController
@RequestMapping("/sys/pest")
public class PestController extends BaseController {

    @PostMapping("/save")
    public Result save(@RequestBody ExpressInfo expressInfo){
        expressInfo.setCreated(LocalDateTime.now());
        expressInfo.setUpdated(LocalDateTime.now());
        expressInfo.setStatu(Const.STATUS_ON);
        expressInfoService.save(expressInfo);
        return Result.succ(expressInfo);
    }

    @PostMapping("/update")
    public Result update(@RequestBody ExpressInfo expressInfo){

        expressInfo.setUpdated(LocalDateTime.now());
        expressInfoService.updateById(expressInfo);

        return Result.succ(expressInfo);
    }

}