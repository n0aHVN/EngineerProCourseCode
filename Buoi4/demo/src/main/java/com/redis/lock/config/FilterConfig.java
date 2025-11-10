package com.redis.lock.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.redis.lock.filter.RateLimiterFilter;

@Configuration
public class FilterConfig {

    // Register the RateLimiterFilter
    // @Bean tells Spring that the method's return value should be managed as a bean
    // @Bean là gì

    // FilterRegistrationBean<RateLimiterFilter> is a helper class for registing filters
    /*
     * It’s used when:
     *      You want to control which URLs the filter applies to
     *      You want to define execution order
     *      Or you want to register multiple filters manually
     */
    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilterRegistration(RateLimiterFilter filter) {  
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}