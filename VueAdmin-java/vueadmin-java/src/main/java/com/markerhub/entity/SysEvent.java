package com.markerhub.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String eventname;
    private Integer num;
    private String region;
    private String location;
    private String sort;
    private LocalDate date;
    private LocalDateTime time;
    private Integer duration;
    private String phone;
    private String leader;
    private String remark;

    @TableField(exist = false)
    private List<SysEvent> sysEvents=new ArrayList<>();


}
