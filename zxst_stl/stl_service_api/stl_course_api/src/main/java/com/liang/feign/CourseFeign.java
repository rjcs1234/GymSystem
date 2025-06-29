package com.liang.feign;

import com.liang.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName CourseFeign
 * @description:
 * @author: 梁海富
 * @create: 2025−06-23 15:11
 * @Version 1.0
 **/
@FeignClient(name = "COURSESERIVE")
public interface CourseFeign {

    //根据教练id查询课程
    @GetMapping("/course/getCourseByID")
    public List<Course> getCourseByID(@RequestParam("id") Integer id);

    //根据课程的id查询课程信息 同时查询课程教练信息
    @GetMapping("/course/getCourseAndCoathByCid")
    public Course getCourseAndCoathByCid(@RequestParam("cid") Integer cid);

}
