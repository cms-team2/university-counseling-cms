package com.counseling.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.counseling.cms.interceptor.AuthHeaderInterceptor;

@Configuration
public class AuthHeaderConfig implements WebMvcConfigurer {

    @Autowired
    private AuthHeaderInterceptor authHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authHeaderInterceptor)
                .addPathPatterns("/admin/**");
    }
}
