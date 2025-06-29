package com.liang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.entity.Admin;
import com.liang.mapper.AdminMapper;
import com.liang.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.util.MD5Util;
import com.liang.vo.AdminVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin login(AdminVo adminVo) throws  Exception{
        if(adminVo.getPhone()==null || adminVo.getPhone().length()<3||adminVo.getPhone().length()>16){
            throw new Exception("手机号码格式错误");
        }
        if(adminVo.getPassword()==null || adminVo.getPassword().length()==0){
            throw  new Exception("密码格式错误");
        }
        LambdaQueryWrapper<Admin> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getPhone,adminVo.getPhone());
        Admin admin = adminMapper.selectOne(lambdaQueryWrapper);

        //验证密码
        String password = adminVo.getPassword();
        String md5 = MD5Util.getMD5(password);
        if(!admin.getPassword().equals(md5)){
            throw new Exception("密码输入错误");
        }
        return admin;
    }
}
