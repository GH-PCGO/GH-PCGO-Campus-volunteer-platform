package com.markerhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExpressInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer type;

    private String title;

    private String message;

    private String img;

    private String username;


}
