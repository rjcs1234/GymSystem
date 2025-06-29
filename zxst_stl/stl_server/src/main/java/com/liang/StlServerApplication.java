package com.liang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StlServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StlServerApplication.class, args);
    }
}
