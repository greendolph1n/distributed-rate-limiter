package com.derekserrano.ratelimiter.interceptor;

import com.derekserrano.ratelimiter.model.RateLimitResult;
import com.derekserrano.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    public RateLimitInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userId = request.getHeader("X-User-Id");

        if (userId == null) {
            userId = "anonymous";
        }

        RateLimitResult allowed = rateLimiterService.allowRequest(userId);
        long remainingTokens = allowed.getRemainingTokens();
        long resetTime = allowed.getResetTime();

        if (!allowed.isAllowed()) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded");
            response.setHeader("X-RateLimit-Limit", "10");
            response.setHeader("X-RateLimit-Remaining", String.valueOf(remainingTokens));
            response.setHeader("X-RateLimit-Reset", String.valueOf(resetTime));
            return false;
        }

        return true;
    }
}