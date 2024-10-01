package com.counseling.cms.utility;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SocketHandlerUtility extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(SocketHandlerUtility.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserIdFromUri(session.getUri());
        if (userId != null) {
        	String msg = "*** " + userId+"님이 입장하셨습니다.";
            sessions.put(userId, session);
            handleTextMessage(session, new TextMessage(msg));
        } else {

        }
    }

    private String extractUserIdFromUri(URI uri) {
        String query = uri.getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && "id".equals(keyValue[0])) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }

    //퇴장
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserIdFromUri(session.getUri());
        if (userId != null) {
        	String msg = "*** " + userId+"님이 퇴장하셨습니다.";
            sessions.remove(userId);            
            handleTextMessage(session, new TextMessage(msg));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 현재 세션의 사용자 ID를 attributes에서 가져옴
        String userId = (String) session.getAttributes().get("userId");
        
        for (WebSocketSession cont : sessions.values()) {

            if (cont.isOpen()) {
                cont.sendMessage(message); // 모든 클라이언트에게 메시지 전송

            }
        }
    }

}
