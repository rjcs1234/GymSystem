package com.liang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxst
 * @since 2025-06-17
 */
@Data
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer status;

    private Double score;

    private String comment;

    private Date commentTime;

    private Date reserveTime;

    private Integer courseId;

    private Integer memberId;

    @TableField (exist = false)
    private Member member;

    @TableField (exist = false)
    private Course course;
}
