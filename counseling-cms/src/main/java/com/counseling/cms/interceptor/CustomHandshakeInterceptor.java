package com.counseling.cms.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.counseling.cms.jwt.JwtUtil;

@Component 
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomHandshakeInterceptor.class);
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        logger.info("afterHandshake 호출됨");
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.info("beforeHandshake 호출됨");

        // 요청 경로에서 ID 추출
        String path = request.getURI().toString();
        logger.info("요청된 경로: {}", path);

        // 쿠키에서 토큰을 추출하여 사용자 ID를 가져옵니다.
        HttpHeaders headers = request.getHeaders();
        String cookieHeader = headers.getFirst(HttpHeaders.COOKIE);

        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");
            for (String cookie : cookies) {
                String[] keyValue = cookie.split("=");
                if (keyValue.length == 2 && "accessToken".equals(keyValue[0])) {
                	System.out.println(cookie);
                	
                    String accessToken = keyValue[1];
                    logger.info("Access Token: {}", accessToken);
                    
                    // 액세스 토큰에서 사용자 ID를 추출합니다.
                    String userId = getUserIdFromAccessToken(accessToken);
                    if (userId != null) {
                        attributes.put("userId", userId); // attributes에 사용자 ID 추가
                        logger.info("User ID added to attributes: {}", userId);
                    } else {
                        logger.warn("Invalid access token.");
                        return false; // 유효하지 않은 토큰일 경우 핸드쉐이크 중단
                    }
                    break; // 액세스 토큰을 찾았으므로 루프 종료
                }
            }
        }


        return true; // 핸드쉐이크 계속 진행
    }

    // 액세스 토큰에서 사용자 ID를 추출하는 메서드
    private String getUserIdFromAccessToken(String accessToken) {
        return jwtUtil.extractUserId(accessToken);
    }
}
