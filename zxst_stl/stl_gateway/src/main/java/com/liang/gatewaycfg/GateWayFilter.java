package com.liang.gatewaycfg;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
public class GateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("过滤器执行了.......");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getQueryParams().getFirst("token");

        // http://localhost:8888/user/login
        String path = request.getURI().getPath();
        // path = /user/login
        if (path.startsWith("/user/login") || path.startsWith("/user/logout")) {
            return chain.filter(exchange);
        }
        if(token!=null && !token.equals("")) {
            //放行
            return chain.filter(exchange);
        }else{
            //拦截
            return  response.setComplete();
        }
    }

    //该方法设定过滤器的执行顺序
    //返回数字越小 优先执行
    @Override
    public int getOrder() {
        return 0;
    }
}
