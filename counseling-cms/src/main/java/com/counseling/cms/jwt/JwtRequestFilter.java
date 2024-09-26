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

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtUtil.extractUserId(token);
            String userAuthority = jwtUtil.extractAuthority(token);
            String userInfo = userId + "," + userAuthority;

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo);

                if (jwtUtil.isTokenExpired(token)) {
                    jwtUtil.removeCookie(res, req);
                    String dbRefreshToken = userDetailsService.getRefreshToken(userId);

                    if (jwtUtil.isTokenExpired(dbRefreshToken)) {
                        loginService.logoutService(res, req);
                    } else {
                        String reToken = jwtUtil.generateToken(userId, dbRefreshToken);
                        jwtUtil.saveCookie(res, reToken);
                    }
                }

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
