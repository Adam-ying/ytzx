package com.yingtao.ytzx.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.yingtao.ytzx.model.entity.user.UserInfo;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-29 18:54
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("path {}", path);

        UserInfo userInfo = this.getUserInfo(request);

        if(antPathMatcher.match("/api/**/auth/**", path)){
            if(userInfo == null){
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(wrap));
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(!CollectionUtil.isEmpty(tokenList)){
            token = tokenList.get(0);
        }
        if(StrUtil.isNotBlank(token)){
            String userInfoJson = redisTemplate.opsForValue().get("user:token:" + token);
            if(StrUtil.isEmpty(userInfoJson)){
                return null;
            }
            return JSON.parseObject(userInfoJson, UserInfo.class);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
