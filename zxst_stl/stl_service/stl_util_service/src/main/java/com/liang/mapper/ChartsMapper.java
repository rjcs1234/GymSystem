package com.liang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: zxst_stl
 * @ClassName ChartsMapper
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 20:02
 * @Version 1.0
 **/

public interface ChartsMapper {


    @Select("select name,count(*) count from course group by name order by count desc limit 5")
    List<Map<String, Object>> gethotCourse(Date date);

    @Select("select date(create_time) day, count(0) count from member\n" +
            "where create_time>=#{date} group by date(create_time)\n" +
            "order by date(create_time) asc limit 7")
    List<Map<String, Object>> getWeekMember(@Param("date") Date date);


    @Select("select coach.`name` name ,avg(reserve.score) count from  coach left join course on coach.id = course.coach_id left join reserve  on  course.id= reserve.course_id where course.time > #{date} GROUP BY coach.id ORDER BY avg(reserve.score)  desc  limit 5")
    List<Map<String, Object>> gethotCoach(@Param("date") Date date);

    @Select("select count(*)  count from member")
    Map<String, Object> memberTotal();


    @Select("select count(*)  count from coach")
    Map coachTotal();

    @Select("select count(*) count from course")
    Map courseTotal();

    @Select("select count(*) count from reserve")
    Map reserveTotal();

    @Select("select  day ,count(*)count from (select  date(time) as day, course.coach_id  from course  where time>#{date} GROUP BY day,course.coach_id )as c GROUP BY day ORDER BY day")
    List<Map<String, Object>> weekCoach(Date date);

    @Select("select  date(time) as day, count(*) count from course where time>#{date} GROUP BY day ORDER BY day asc")
    List<Map<String, Object>> weekCourse(Date date);

    @Select("select  date(reserve.reserve_time) as day, count(*) count from reserve where reserve.reserve_time>#{date} GROUP BY day ORDER BY day asc")
    List<Map<String, Object>> weekReserve(Date date);
}
