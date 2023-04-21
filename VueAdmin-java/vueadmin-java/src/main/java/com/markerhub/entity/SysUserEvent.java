package com.markerhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long eventId;


}
