package com.liang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.entity.Reserve;
import com.liang.util.Result;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxst
 * @since 2025-06-17
 */

public interface IReserveService extends IService<Reserve> {


    Result findCoachPage(Integer currentPage, Integer pageSize, Integer mid, Integer courseId);
}
