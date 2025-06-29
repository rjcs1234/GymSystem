package com.liang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
@Data
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date time;

    private Integer length;

    private Integer maxCount;

    private String room;

    private String photo;

    private Integer coachId;

    @TableField(exist = false) // 标记非数据库字段
    private Coach coach;
    @TableField(exist = false) // 标记非数据库字段
    private Long reserveCount;

}
