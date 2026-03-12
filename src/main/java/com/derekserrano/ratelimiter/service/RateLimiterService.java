package com.derekserrano.ratelimiter.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private final DefaultRedisScript<Long> script;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;

        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("scripts/token_bucket.lua"));
        script.setResultType(Long.class);
    }

    public boolean allowRequest(String userId) {

        String key = "bucket:" + userId;

        List<String> keys = List.of(key);

        Long result = redisTemplate.execute(
                script,
                keys,
                "10",   // capacity
                "0.1",  // refill rate
                String.valueOf(System.currentTimeMillis() / 1000)
        );

        return result != null && result == 1;
    }
}