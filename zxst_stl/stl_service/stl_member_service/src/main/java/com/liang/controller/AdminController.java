package com.liang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.entity.Admin;
import com.liang.service.IAdminService;
import com.liang.util.JWTUtil;
import com.liang.util.MD5Util;
import com.liang.util.Result;
import com.liang.vo.AdminVo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IAdminService iAdminService;

    @PostMapping("/login")
    public Result login(@RequestBody AdminVo adminVo) throws  Exception{
        try {

            //验证码的校验
            String key = adminVo.getKey();
            if(!StringUtils.hasText(key)){
                throw new Exception("无效的验证码");
            }
            Claims claims=null;
            String localCaptcha=null;
            try{
                claims= JWTUtil.parseJWT(key);
                //解析出验证码的主题
                localCaptcha=claims.getSubject();
            }catch (Exception e){
                throw new Exception("验证码过期");
            }
            if(!localCaptcha.equalsIgnoreCase(adminVo.getCaptcha())){
                throw new Exception("验证码输入错误");
            }

            Admin admin=iAdminService.login(adminVo);

            String  token=JWTUtil.createJWT(admin.getPhone());
            return Result.success(token);
        }catch (Exception e){
            return  Result.error(e.getMessage());
        }
    }

    @GetMapping("/getAdminInfo")
    public  Result getAdminInfo(HttpServletRequest request) throws  Exception{
        //请求头中发送的登录成功后存放在pinia的jwt消息
        String token = request.getHeader("token");
        if(StringUtils.hasText(token)){
            try {
                //解析jwt消息串
                Claims claims=JWTUtil.parseJWT(token);
                String phone = claims.getSubject();
                LambdaQueryWrapper<Admin> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Admin::getPhone,phone);
                Admin one = iAdminService.getOne(lambdaQueryWrapper);
                if(!ObjectUtils.isEmpty(one)){
                    return Result.success(one);
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        return Result.error("用户未登录");
    }

    //分页展示管理员信息
    @GetMapping
    public Result showAdminInfo(
            @RequestParam(name = "currentPage",required = false,defaultValue = "1")Integer currentPage,
            @RequestParam(name = "pageSize" ,required = false,defaultValue = "10") Integer pageSize,
            String search ){
        Page<Admin> page=new Page<>(currentPage,pageSize);
        LambdaQueryWrapper <Admin> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(search!=null && !"".equals(search)){
            lambdaQueryWrapper.like(Admin::getName,search);
            lambdaQueryWrapper.or().like(Admin::getPhone,search);
        }
        Page<Admin> page1 = iAdminService.page(page, lambdaQueryWrapper);
        return  Result.success(page);
    }

    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Admin admin) throws  Exception{
        try {
            LambdaQueryWrapper<Admin> lambdaQueryWrapper=new LambdaQueryWrapper<Admin>();
            lambdaQueryWrapper.eq(Admin::getPhone,admin.getPhone());
            Admin duAdmin = iAdminService.getOne(lambdaQueryWrapper);
            if(duAdmin!=null){
                //已经存在
                throw new Exception("手机号已存在");
            }else{
                String md5 = MD5Util.getMD5(admin.getPassword(),admin.getName());
                admin.setPassword(md5);
                boolean save = iAdminService.save(admin);
                if(save){
                    return  Result.success();
                }else{
                    return  Result.error();
                }
            }
        }catch (Exception e){
            return  Result.error(e.getMessage());
        }
    }

    //更新管理员信息
    @PostMapping("/updateAdminInfo")
    public Result updateAdminInfo(@RequestBody Admin admin) {
        boolean update = iAdminService.updateById(admin);
        if(update){
            return Result.success();
        }else{
            return Result.error();
        }
    }

}
