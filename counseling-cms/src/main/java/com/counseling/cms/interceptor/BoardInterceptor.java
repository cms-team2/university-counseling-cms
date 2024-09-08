package com.counseling.cms.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BoardInterceptor implements HandlerInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(BoardInterceptor.class);
    private static final String REQUIRED_BOARD_ID[] = {"notice","review","inquiry","dataroom","program","faq"}; // 허용된 userId 값
    private static final String REQUIRED_BOARD_NAME[] = {"공지사항","상담 후기","1:1 문의","자료실","상담 프로그램","FAQ"};
    
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        String requestURI = request.getRequestURI();
        String boardId = extractBoardIdFromUri(requestURI);
       
        boolean contains = false;
        int idx = 0;
        for (String element : REQUIRED_BOARD_ID) {
            if (element.equals(boardId)) {
                contains = true;
                
                break;
            }
            
            idx++;
        }

        if(contains == true) {
            // userId가 "notice"이면 요청을 계속 처리
        	request.setAttribute("boardName", REQUIRED_BOARD_NAME[idx]);
        	request.setAttribute("boardId", REQUIRED_BOARD_ID[idx]);
        	
            return true;
        } else {
            logger.warn("Access denied for userId '{}'", boardId);
            response.sendRedirect("/");
            return false; // 요청 처리를 중단
        }
	}
	
    private String extractBoardIdFromUri(String uri) {
        String[] parts = uri.split("/");
        return parts.length > 2 ? parts[2] : null;
    }
	
}
