package com.liang.feign;

/**
 * @program: zxst_stl
 * @ClassName CoachFeign
 * @description:
 * @author: 梁海富
 * @create: 2025−06-26 17:07
 * @Version 1.0
 **/

import com.liang.entity.Coach;
import com.liang.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MEMBERSERVICE")
public interface CoachFeign {

    @GetMapping("/coach/getOneCocaById")
    public Coach getOneCoachById(@RequestParam("id") Integer id);

    @GetMapping("/member/getOneMemeber") // 确认路径与服务提供者一致
    public Member getOneMemeber(@RequestParam("id") Integer id);

}
