package com.liang.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.entity.Coach;
import com.liang.entity.Course;
import com.liang.feign.CoachFeign;
import com.liang.feign.ReserveFeign;
import com.liang.mapper.CourseMapper;
import com.liang.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CoachFeign coachFeign;

    @Resource
    private ReserveFeign reserveFeign;
    @Override
    public Result getCourseInfo(Integer currentPage, Integer pageSize, Course course) {
        LambdaQueryWrapper<Course> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        System.out.println(course+"===");
        if (course != null && course.getName()!= null && !"".equals(course.getName())) {
            lambdaQueryWrapper.like(Course::getName, course.getName());
        }
        if (course != null && course.getCoachId()!= null){
            lambdaQueryWrapper.eq(Course::getCoachId,course.getCoachId());
        }
        Page<Course> page=new Page<>(currentPage,pageSize);
        courseMapper.selectPage(page,lambdaQueryWrapper);
        List<Course> records = page.getRecords();
        for(Course course1: records){
            //查询课程教练
            Integer coachId = course1.getCoachId();
            Coach one = coachFeign.getOneCoachById(coachId);
            course1.setCoach(one);
            //查询课程的预约认识
            Long reserveCountByCid = reserveFeign.getReserveCountByCid(course1.getId());
            course1.setReserveCount(reserveCountByCid);
        }
        return Result.success(0,null,page);
    }

    @Override
    public Result findAllCourse() {
        List<Course> courses = courseMapper.selectList(null);
        for (Course cours : courses) {
            //查询课程教练
            Integer coachId = cours.getCoachId();
            Coach oneCoachById = coachFeign.getOneCoachById(coachId);
            cours.setCoach(oneCoachById);
            //查询课程预约人数
            Long reserveCountByCid = reserveFeign.getReserveCountByCid(cours.getId());
            cours.setReserveCount(reserveCountByCid);
        }
        return Result.success(0,null,courses);
    }

    @Override
    public Course getCourseAndCoathByCid(Integer cid) {
        Course course = courseMapper.selectById(cid);
        Coach oneCoachById = coachFeign.getOneCoachById(course.getCoachId());
        course.setCoach(oneCoachById);
        return course;
    }
}
