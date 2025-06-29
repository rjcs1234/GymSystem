package com.liang.controller;
import com.liang.entity.Reserve;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.service.IReserveService;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName ReserveController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-17 18:50
 * @Version 1.0
 **/


@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Resource
    private IReserveService iReserveService;

    @GetMapping("/getReserveByMid")
    public List<Reserve> getReserveByMid (@RequestParam("mid") Integer mid){
        LambdaQueryWrapper<Reserve> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reserve::getMemberId, mid);
        List<Reserve> list = iReserveService.list(lambdaQueryWrapper);
        return list;
    }
    @GetMapping("/getReserveCountByCid")
    public Long getReserveCountByCid(@RequestParam("cid") Integer cid) {
        LambdaQueryWrapper<Reserve> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reserve::getCourseId,cid);
        long count = iReserveService.count(lambdaQueryWrapper);
        return count;
    }


    //分页展示某个课程的预约的学员信息
    @GetMapping
    public Result findCoachPage(
            @RequestParam(name = "currentPage",required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(name = "courseId",required = false,defaultValue = "0") Integer courseId,
            Integer memberId) {
        Result result = iReserveService.findCoachPage(currentPage,pageSize,memberId,courseId);
        return result;
    }

    //添加预约信息
    @PostMapping
    public Result addReserve(@RequestBody Reserve reserve) {
        reserve.setStatus(1);
        reserve.setReserveTime(new java.util.Date());
        boolean save = iReserveService.save(reserve);
        if(save){
            return Result.success();
        }
        return Result.error("添加预约失败");
    }

    //取消预约
    @PutMapping
    public Result editReserve(@RequestBody Reserve reserve) {
        iReserveService.updateById(reserve);
        return Result.success();
    }

}
