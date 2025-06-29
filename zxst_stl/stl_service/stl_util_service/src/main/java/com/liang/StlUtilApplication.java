package com.liang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.liang.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class StlUtilApplication {
    public static void main(String[] args) {
        SpringApplication.run(StlUtilApplication.class, args);
    }
}
