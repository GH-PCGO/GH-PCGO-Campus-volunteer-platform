package com.markerhub.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    private String eventname;

    /**
     * 活动图片
     */
    private String img;

    /**
     * 活动人数
     */
    private Integer num;

    /**
     * 活动地点
     */
    private String location;

    /**
     * 活动日期
     */
    private LocalDate date;

    /**
     * 开始时间
     */
    private LocalTime time;

    /**
     * 活动时长
     */
    private Integer duration;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 创建人
     */
    private String leader;

    /**
     * 活动备注
     */
    private String remark;


}
