package com.liang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.entity.Course;
import com.liang.entity.Member;
import com.liang.entity.Reserve;
import com.liang.feign.CoachFeign;
import com.liang.feign.CourseFeign;
import com.liang.mapper.ReserveMapper;
import com.liang.service.IReserveService;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxst
 * @since 2025-06-17
 */
@Service
public class ReserveServiceImpl extends ServiceImpl<ReserveMapper, Reserve> implements IReserveService {

    @Resource
    private ReserveMapper reserveMapper;

    @Resource
    private CoachFeign coachFeign;

    @Resource
    private CourseFeign courseFeign;
    @Override
    public Result findCoachPage(Integer currentPage, Integer pageSize,
                                Integer mid,Integer courseId) {
        Page<Reserve> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Reserve> lambdaQueryWrapper = new
                LambdaQueryWrapper<>();
        if (mid != null && mid > 0) {
            lambdaQueryWrapper.eq(Reserve::getMemberId,mid);
        }
        if(courseId!=null&&courseId!=0){
            lambdaQueryWrapper.eq(Reserve::getCourseId,courseId); //指定课程的预约信息
        }
        lambdaQueryWrapper.eq(Reserve::getStatus,1);//预约成功的预约信息
        reserveMapper.selectPage(page,lambdaQueryWrapper);
        for (Reserve reserve : page.getRecords()) {
            Integer memberId = reserve.getMemberId();
            Member oneMemeber = coachFeign.getOneMemeber(memberId);
            reserve.setMember(oneMemeber);
            //课程id
            Integer courseId1 = reserve.getCourseId();
            Course courseAndCoathByCid = courseFeign.getCourseAndCoathByCid(courseId1);
            reserve.setCourse(courseAndCoathByCid);
        }
        return Result.success(0,null,page);
    }
}

