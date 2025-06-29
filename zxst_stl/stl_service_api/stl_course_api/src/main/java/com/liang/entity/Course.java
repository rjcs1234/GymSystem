package com.liang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    //授课教练
    @TableField(exist = false)
    private  Coach coach;

    //课程预约人数
    @TableField(exist = false)
    private  Long reserveCount;
}
