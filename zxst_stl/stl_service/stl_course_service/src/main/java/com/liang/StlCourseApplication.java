package com.liang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.liang.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class StlCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(StlCourseApplication.class, args);
    }
}
