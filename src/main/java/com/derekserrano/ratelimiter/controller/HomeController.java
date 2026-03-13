package com.derekserrano.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
        Distributed Rate Limiter API

        Test endpoint:
        /api/test

        Example:
        curl -H "X-User-Id: user1" https://distributed-rate-limiter-7pn5.onrender.com/api/test
        """;
    }
}