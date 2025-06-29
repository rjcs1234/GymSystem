package com.liang.controller;

import com.liang.service.ChartsService;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: zxst_stl
 * @ClassName ChartsController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 19:55
 * @Version 1.0
 **/

@RestController
@RequestMapping("/charts")
public class ChartsController {

    @Resource
    private ChartsService chartsService;


    @GetMapping("/hotCourse")
    public Result gethotCourse(Date date){
        List<Map<String,Object>> list =chartsService.gethotCourse(date);
        return  Result.success(list);
    }

    @GetMapping("/weekMember")
    public Result getWeekMember(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String, Object>> list = chartsService.getWeekMember(date);
        return Result.success(list);
    }


    @GetMapping("/hotCoach")
    public Result hotCoach(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String, Object>> list = chartsService.gethotCoach(date);
        return Result.success(list);
    }

    @GetMapping("/memberTotal")
    public Result memberTotal() {
         Map<String,Object> map = chartsService.memberTotal();
        return Result.success(0,null,map);
    }

    @GetMapping("/coachTotal")
    public Result coachTotal() {
        Map map = chartsService.coachTotal();
        return Result.success(map);
    }

    @GetMapping("/courseTotal")
    public Result courseTotal() {
        Map map = chartsService.courseTotal();
        return Result.success(map);
    }

    @GetMapping("/reserveTotal")
    public Result reserveTotal() {
        Map map = chartsService.reserveTotal();
        return Result.success(map);
    }

    //每天带课教练
    @GetMapping("/weekCoach")
    public Result weekCoach(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String,Object>> list = chartsService.weekCoach(date);
        return Result.success(list);
    }

    //每日课程数量
    @GetMapping("/weekCourse")
    public Result weekCourse(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String,Object>> list = chartsService.weekCourse(date);
        return Result.success(list);
    }

    //每日预约数量
    @GetMapping("/weekReserve")
    public Result weekReserve(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String,Object>> list = chartsService.weekReserve(date);
        return Result.success(list);
    }

    //预约折线图
    @GetMapping("/reserveCount")
    public Result reserveCount(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Map<String,Object>> list = chartsService.weekReserve(date);
        return Result.success(list);
    }



}
