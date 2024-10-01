package com.counseling.cms.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String requestUri = request.getRequestURI();

	    // 인증되지 않은 사용자의 특정 경로 접근 시 리디렉션
	    if (requestUri.startsWith("/admin")) {
	        response.sendRedirect("/admin/login"); 
	    } else if(requestUri.startsWith("/user") || requestUri.startsWith("/counselor")){
	    	response.sendRedirect("/user/login");
	    } else {
	        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
	    }
		
	}
}
