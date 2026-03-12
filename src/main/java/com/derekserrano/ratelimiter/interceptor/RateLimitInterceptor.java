package com.derekserrano.ratelimiter.interceptor;

import com.derekserrano.ratelimiter.config.RateLimiterProperties;
import com.derekserrano.ratelimiter.model.RateLimitResult;
import com.derekserrano.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;
    private final RateLimiterProperties rateLimiterProperties;

    public RateLimitInterceptor(RateLimiterService rateLimiterService, RateLimiterProperties rateLimiterProperties) {
        this.rateLimiterService = rateLimiterService;
        this.rateLimiterProperties = rateLimiterProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userId = request.getHeader("X-User-Id");

        if (userId == null) {
            userId = "anonymous";
        }

        RateLimitResult result = rateLimiterService.allowRequest(userId);

        response.setHeader("X-RateLimit-Limit", String.valueOf(rateLimiterProperties.getCapacity()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(result.getRemainingTokens()));
        response.setHeader("X-RateLimit-Reset", String.valueOf(result.getResetTime()));


        if (!result.isAllowed()) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded");
            return false;
        }

        return true;
    }
}