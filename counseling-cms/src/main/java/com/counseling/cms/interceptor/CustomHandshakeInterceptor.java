package com.counseling.cms.interceptor;

import java.util.Map;

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
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		 System.out.println("afterHandshake 호출됨");
		
	}
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
	                               Map<String, Object> attributes) throws Exception {
		System.out.println("beforeHandshake 호출됨");
		
	    HttpHeaders headers = request.getHeaders();
	    String cookieHeader = headers.getFirst(HttpHeaders.COOKIE);

	    if (cookieHeader != null) {
	        String[] cookies = cookieHeader.split("; ");
	        for (String cookie : cookies) {
	            String[] keyValue = cookie.split("=");
	            if (keyValue.length == 2 && "accessToken".equals(keyValue[0])) {
	                String accessToken = keyValue[1];
	                System.out.println("Access Token: " + accessToken);
	                
	                // 액세스 토큰을 사용하여 사용자 정보 추출
	                String userId = getUserIdFromAccessToken(accessToken);
	                if (userId != null) {
	                    attributes.put("userId", userId); // attributes에 사용자 ID 추가
	                    System.out.println("User ID added to attributes: " + userId);
	                } else {
	                    System.out.println("Invalid access token.");
	                    return false; // 유효하지 않은 토큰일 경우 핸드쉐이크 중단
	                }
	                break; // 액세스 토큰을 찾았으므로 루프 종료
	            }
	        }
	    }

	    return true; // 핸드쉐이크 계속 진행
	}

	// 액세스 토큰에서 사용자 ID를 추출하는 메서드 (예시)
	private String getUserIdFromAccessToken(String accessToken) {
		String userId = jwtUtil.extractUserId(accessToken);
	    return userId; // 실제 구현으로 대체
	}

}
