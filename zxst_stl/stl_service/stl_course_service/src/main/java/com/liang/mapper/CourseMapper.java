package com.liang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.entity.Coach;
import com.liang.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liang
 * @since 2025-06-19
 */
public interface CourseMapper extends BaseMapper<Course> {



    // 使用注解方式实现分页查询
    @Select("SELECT * FROM course LIMIT #{offset}, #{size}")
    List<Course> getCourseInfo(@Param("offset") long offset, @Param("size") long size);

    @Select("select * from coach where id=#{coach_id}")
    Coach getCoachByID(@Param("coach_id") int coachId);


    @Select("select count(*) from reserve where course_id=#{course_id}")
    int getReseverCountByid(@Param("course_id") Integer Id);
}
