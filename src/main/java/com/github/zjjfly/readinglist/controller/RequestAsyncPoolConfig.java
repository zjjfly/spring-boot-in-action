package com.github.zjjfly.readinglist.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.context.request.async.TimeoutDeferredResultProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class RequestAsyncPoolConfig implements WebMvcConfigurer {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(60 * 1000);
        configurer.setTaskExecutor(threadPoolTaskExecutor);
        configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
        configurer.registerDeferredResultInterceptors(timeoutDeferredResultProcessingInterceptor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public TimeoutDeferredResultProcessingInterceptor timeoutDeferredResultProcessingInterceptor() {
        return new TimeoutDeferredResultProcessingInterceptor();
    }
}
