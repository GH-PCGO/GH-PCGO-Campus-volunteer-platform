package com.markerhub.service;

import com.markerhub.entity.SysEvent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pc
 * @since 2023-04-14
 */
public interface SysEventService extends IService<SysEvent> {

    List<SysEvent> listEventsByUserId(Long userId);

}
