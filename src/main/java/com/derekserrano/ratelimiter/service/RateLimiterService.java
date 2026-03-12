package com.derekserrano.ratelimiter.service;

import com.derekserrano.ratelimiter.config.RateLimiterProperties;
import com.derekserrano.ratelimiter.model.RateLimitResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private final DefaultRedisScript<List> script;

    private final RateLimiterProperties properties;

    public RateLimiterService(StringRedisTemplate redisTemplate,
                              RateLimiterProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;

        script = new DefaultRedisScript<List>();
        script.setLocation(new ClassPathResource("scripts/token_bucket.lua"));
        script.setResultType(List.class);
    }

    public RateLimitResult allowRequest(String userId) {

        String key = "bucket:" + userId;

        List<String> keys = List.of(key);

        List<Long> result = redisTemplate.execute(
                script,
                List.of(key),
                String.valueOf(properties.getCapacity()),
                String.valueOf(properties.getRefillRate()),
                String.valueOf(System.currentTimeMillis() / 1000)
        );

        boolean allowed = result.get(0) == 1;
        long remaining = result.get(1);
        long reset = result.get(2);

        return new RateLimitResult(allowed, remaining, reset);
    }
}