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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null) {
            String accessToken = CookieUtility.getCookie(req, "accessToken");
            if (accessToken != null) {
                authHeader = "Bearer " + accessToken;
                res.setHeader("Authorization", authHeader);
            }
        }

        // Authorization 헤더가 존재하고 Bearer로 시작할 때
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtUtil.extractUserId(token);
            String userAuthority = jwtUtil.extractAuthority(token);
            String userInfo=userId+","+userAuthority;
            
            // 유저 이름이 존재하고 현재 인증 정보가 없는 경우
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo);
                // 토큰이 만료되었을 때
                if (jwtUtil.isTokenExpired(token)) {
                	
                	jwtUtil.removeCookie(res, req);		//만료된 토큰 삭제 
                	
                	//refreshToken 만료 여부 확인
                	String dbRefreshToken=userDetailsService.getRefreshToken(userId);
                	
                	if(jwtUtil.isTokenExpired(dbRefreshToken)) {
                		loginService.logoutService(res, req);		//만료시 로그아웃                		
                	} else {
                		String reToken=jwtUtil.generateToken(userId, dbRefreshToken);	//refreshToken으로 accessToken 재발급
                		
                		//HttpOnly 쿠키에 accessToken 저장
            			jwtUtil.saveCookie(res, reToken);
                	
                	}
                }
                // 유효한 토큰일 경우(권한 검사)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                if (req.getRequestURI().equals("/admin/login")) {
                    res.sendRedirect("/admin/apply-list");
                    return;
                }
            }
        }
        
        filterChain.doFilter(req, res);
    }
}
