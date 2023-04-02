package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告内容
     */
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @TableField(exist = false)
    private List<SysNotice> sysNotices = new ArrayList<>();


}
