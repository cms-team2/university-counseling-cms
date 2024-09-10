package com.counseling.cms.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthHeaderInterceptor implements HandlerInterceptor {
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 	String accessToken = CookieUtility.getCookie(request, "accessToken");
		 	response.setHeader("Authorization", "Bearer " + accessToken);
	        
	        
	        return true;
	    }
}
