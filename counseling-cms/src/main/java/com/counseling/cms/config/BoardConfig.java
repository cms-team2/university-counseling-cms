package com.counseling.cms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.counseling.cms.interceptor.BoardInterceptor;

@Configuration
public class BoardConfig implements WebMvcConfigurer{
	private final BoardInterceptor boardInterceptor;
	
	public BoardConfig (BoardInterceptor boardInterceptor) {
		this.boardInterceptor = boardInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(boardInterceptor)
        .addPathPatterns("/board/**");
	}
}
