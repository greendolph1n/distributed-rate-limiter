package com.derekserrano.ratelimiter.model;

public class RateLimitResult {

    private final boolean allowed;
    private final long remainingTokens;
    private final long resetTime;

    public RateLimitResult(boolean allowed, long remainingTokens, long resetTime) {
        this.allowed = allowed;
        this.remainingTokens = remainingTokens;
        this.resetTime = resetTime;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public long getRemainingTokens() {
        return remainingTokens;
    }

    public long getResetTime() {
        return resetTime;
    }
}