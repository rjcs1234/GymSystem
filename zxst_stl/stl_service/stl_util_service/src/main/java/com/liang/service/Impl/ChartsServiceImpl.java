package com.liang.service.Impl;

import com.liang.mapper.ChartsMapper;
import com.liang.service.ChartsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: zxst_stl
 * @ClassName ChartsServiceImpl
 * @description:
 * @author: 梁海富
 * @create: 2025−06-19 20:01
 * @Version 1.0
 **/

@Service
public class ChartsServiceImpl implements ChartsService {

    @Resource
    private ChartsMapper chartsMapper;

    @Override
    public List<Map<String, Object>> gethotCourse(Date date) {
        return chartsMapper.gethotCourse(date);
    }

    @Override
    public List<Map<String, Object>> getWeekMember(Date date) {
        return  chartsMapper.getWeekMember(date);
    }

    @Override
    public List<Map<String, Object>> gethotCoach(Date date) {
        return chartsMapper.gethotCoach(date);
    }


    @Override
    public Map memberTotal() {
        return chartsMapper.memberTotal();
    }

    @Override
    public Map coachTotal() {
        return chartsMapper.coachTotal();
    }

    @Override
    public Map courseTotal() {
        return chartsMapper.courseTotal();
    }

    @Override
    public Map reserveTotal() {
        return chartsMapper.reserveTotal();
    }

    @Override
    public List<Map<String, Object>> weekCoach(Date date) {
        return chartsMapper.weekCoach(date);
    }

    @Override
    public List<Map<String, Object>> weekCourse(Date date) {
        return chartsMapper.weekCourse(date);
    }

    @Override
    public List<Map<String, Object>> weekReserve(Date date) {
        return chartsMapper.weekReserve(date);
    }
}
