package com.liang.service;

import com.liang.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.vo.AdminVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
public interface IAdminService extends IService<Admin> {

    Admin login(AdminVo adminVo) throws  Exception;
}
