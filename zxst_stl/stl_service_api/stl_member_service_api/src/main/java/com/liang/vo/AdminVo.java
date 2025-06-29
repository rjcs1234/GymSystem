package com.liang.vo;

/**
 * @program: zxst_stl
 * @ClassName AdminVo
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 16:59
 * @Version 1.0
 **/


import com.liang.entity.Admin;
import lombok.Data;


@Data
public class AdminVo extends Admin {
    //登录
    private String key;
    private String captcha;
}