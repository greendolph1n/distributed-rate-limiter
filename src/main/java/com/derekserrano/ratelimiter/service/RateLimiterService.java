package com.derekserrano.ratelimiter.service;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    public boolean allowRequest(String userId) {
        return true; // temporary
    }

}