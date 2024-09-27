package com.counseling.cms.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestUri = request.getRequestURI();

        // 권한 부족 시 리디렉션
        if (requestUri.startsWith("/admin")) {
            response.sendRedirect("/accessDenied1");
        } else if (requestUri.startsWith("/user") || requestUri.startsWith("/counselor")) {
            response.sendRedirect("/accessDenied2");
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Access is denied");
        }
    }
}
