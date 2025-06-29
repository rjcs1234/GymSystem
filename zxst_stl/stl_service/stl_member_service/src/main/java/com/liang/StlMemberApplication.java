package com.liang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@MapperScan("com.liang.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  //开启feign调用
public class StlMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(StlMemberApplication.class, args);
    }
}

