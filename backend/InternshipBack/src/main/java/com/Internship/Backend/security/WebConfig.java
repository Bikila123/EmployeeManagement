package com.Internship.Backend.security;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/api/auth/**", "/User/empIdexists/**", "/User/empIdHrexists/**", "/User/getunits", "/User/getjobpositions","/User/phoneexists/**", "/User/emailexists/**", "/User/signup", "/User/createpassword");
    }
}

