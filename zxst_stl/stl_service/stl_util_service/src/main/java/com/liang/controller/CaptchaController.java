package com.liang.controller;

import com.liang.util.CaptchaUtil;
import com.liang.util.JWTUtil;
import com.liang.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: zxst_stl
 * @ClassName CaptchaController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 14:46
 * @Version 1.0
 **/

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @GetMapping
    public Result captcha(){
        //验证码字符串
        String captcha= CaptchaUtil.randomCode(5);
        //将字符串生成图片
        String img=CaptchaUtil.createBase64(captcha);
        //使用jwt工具类
        String jwt = JWTUtil.createJWT(captcha, 10 * 60 * 1000L);
        return  Result.success(0,jwt,img);
    }
}
