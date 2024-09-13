package com.counseling.cms.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.counseling.cms.service.LoginService;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            String accessToken = CookieUtility.getCookie(request, "accessToken");
            if (accessToken != null) {
                authHeader = "Bearer " + accessToken;
                response.setHeader("Authorization", authHeader);
            }
        }
        
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/pw/") || requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/images/") || requestURI.equals("/admin/login") ) {
        	filterChain.doFilter(request, response);
            return;
        }
        // Authorization 헤더가 존재하고 Bearer로 시작할 때
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtUtil.extractUserId(token);
            
            // 유저 이름이 존재하고 현재 인증 정보가 없는 경우
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                // 토큰이 만료되었을 때
                if (jwtUtil.isTokenExpired(token)) {
                	
                	//refreshToken 만료 여부 확인
                	String dbRefreshToken=userDetailsService.getRefreshToken(userId);
                	if(jwtUtil.isTokenExpired(dbRefreshToken)) {
                		loginService.logoutService(response, request);		//만료시 로그아웃                		
                	} else {
                		jwtUtil.generateToken(userId, dbRefreshToken);
                	}
                    return;
                }
                // 유효한 토큰일 경우
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                if (request.getRequestURI().equals("/admin/login")) {
                    response.sendRedirect("/admin/apply-list");
                    return;
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
