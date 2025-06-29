package com.liang.service;

import com.liang.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
public interface ICourseService extends IService<Course> {


    Result getCourseInfo(Integer currentPage, Integer pageSize, Course course);

    Result findAllCourse();

    Course getCourseAndCoathByCid(Integer cid);
}
