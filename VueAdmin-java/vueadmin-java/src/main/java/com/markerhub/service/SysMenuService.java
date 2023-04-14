package com.markerhub.service;

import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author pc
 * @date
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserNav();

    List<SysMenu> tree();

}
