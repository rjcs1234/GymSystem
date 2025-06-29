package com.liang.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.entity.Course;
import com.liang.service.ICourseService;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName courseController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-23 15:23
 * @Version 1.0
 **/
@RestController
@RequestMapping("/course")
public class courseController {
    @Resource
    private ICourseService iCourseService;

    //根据教练id查询课程
    @GetMapping("/getCourseByID")
    public List<Course> getCourseByID(@RequestParam("id") Integer id){
        LambdaQueryWrapper<Course> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getCoachId,id);
        List<Course> list = iCourseService.list(lambdaQueryWrapper);
        return  list;
    }

    @RequestMapping("/getCourseInfo")
    public Result getCourseInfo(
            @RequestParam(name = "currentPage",required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "3") Integer pageSize,
            Course course) {
        Result result = iCourseService.getCourseInfo(currentPage,pageSize,course);
        return result;
    }

    //添加课程
    @PostMapping("/addCourse")
    public  Result addCourse(@RequestBody Course course){
        boolean save = iCourseService.save(course);
        if(save){
            return Result.success();
        }else{
            return Result.error("添加课程失败");
        }
    }

    @GetMapping("/all")
    public Result findAll() {
        return iCourseService.findAllCourse();
    }

    //根据课程id查询课程信息(同时查询该课程授课教练)
    @GetMapping("/getCourseAndCoathByCid")
    //添加预约
    public Course getCourseAndCoathByCid(@RequestParam("cid") Integer cid){
        return iCourseService.getCourseAndCoathByCid(cid);
    }


}
