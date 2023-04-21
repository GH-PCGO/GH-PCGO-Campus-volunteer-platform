package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.SysEvent;
import com.markerhub.mapper.SysEventMapper;
import com.markerhub.service.SysEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-04-14
 */
@Service
public class SysEventServiceImpl extends ServiceImpl<SysEventMapper, SysEvent> implements SysEventService {

    @Override
    public List<SysEvent> listEventsByUserId(Long userId) {
        List<SysEvent> sysEvents = this.list(new QueryWrapper<SysEvent>()
                .inSql("id","select event_id from sys_user_event where user_id = " + userId));
        return sysEvents;
    }

}

