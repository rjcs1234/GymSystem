package com.liang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@SpringBootApplication
@EnableDiscoveryClient
public class StlGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(StlGateWayApplication.class, args);
    }

    //构建一个限流的工具类(ip地址限流 统一ip地址 1秒中发送一个请求)
    @Bean(name = "ipkeyResolver")
    public KeyResolver ipkeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                String  hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
                return Mono.just(hostAddress);
            }
        };
    }




}
