package com.liang.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: zxst_stl
 * @ClassName ChartsService
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 20:00
 * @Version 1.0
 **/

public interface ChartsService {

    List<Map<String, Object>> gethotCourse(Date date);

    List<Map<String, Object>> getWeekMember(Date date);

    List<Map<String, Object>> gethotCoach(Date date);


    Map<String ,Object> memberTotal();

    Map coachTotal();

    Map courseTotal();

    Map reserveTotal();

    List<Map<String, Object>> weekCoach(Date date);

    List<Map<String, Object>> weekCourse(Date date);

    List<Map<String, Object>> weekReserve(Date date);
}
