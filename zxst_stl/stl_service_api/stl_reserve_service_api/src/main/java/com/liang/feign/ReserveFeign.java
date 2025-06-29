package com.liang.feign;

import com.liang.entity.Reserve;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName ReserveFeign
 * @description:
 * @author: 梁海富
 * @create: 2025−06-17 17:30
 * @Version 1.0
 **/

@FeignClient(name = "RESERVESERVICE")
public interface ReserveFeign {
    @GetMapping("/reserve/getReserveByMid")
    public List<Reserve> getReserveByMid (@RequestParam("mid") Integer mid);

    @GetMapping("/reserve/getReserveCountByCid")
    public Long getReserveCountByCid(@RequestParam("cid") Integer cid) ;
}
