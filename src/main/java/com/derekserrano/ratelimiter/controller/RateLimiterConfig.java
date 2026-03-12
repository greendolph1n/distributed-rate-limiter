package com.derekserrano.ratelimiter.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ratelimiter")
public class RateLimiterConfig {
    private int capacity;
    private double refillRate;
}
