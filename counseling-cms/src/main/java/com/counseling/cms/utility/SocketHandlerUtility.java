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
            sessions.put(userId, session);
            System.out.println("새 클라이언트와 연결되었습니다. User ID: " + userId);
        } else {
            System.out.println("유효하지 않은 사용자 ID입니다.");
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
            sessions.remove(userId);
            System.out.println("클라이언트가 퇴장했습니다. User ID: " + userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 현재 세션의 사용자 ID를 attributes에서 가져옴
        String userId = (String) session.getAttributes().get("userId");
        
        // 사용자로부터 받은 메시지 출력
        System.out.println(userId+":"+message.getPayload());

        if (message.getPayload().startsWith("수락 요청")) {
            String targetUserId = message.getPayload().split(" ")[2]; // 상대방 ID 추출
            sendAcceptRequest(targetUserId, userId); // 수락 요청 전송
        }else if (message.getPayload().startsWith("accept")) {
            String requesterId = message.getPayload().split(" ")[1]; // 요청자 ID 추출
            handleAcceptance(requesterId); // 수락 요청 처리
        } else {
            // 일반 메시지 처리
            for (WebSocketSession cont : sessions.values()) {
            	System.out.println(cont);
                if (cont.isOpen()) {
                    cont.sendMessage(message); // 모든 클라이언트에게 메시지 전송
                }
            }
        }
    }

    // 특정 사용자에게 수락 요청 메시지 전송하는 메서드
    public void sendAcceptRequest(String targetUserId, String requesterId) {
        WebSocketSession session = sessions.get(targetUserId);
        if (session != null && session.isOpen()) {
            try {
            	String message = "";
            	if(session.isOpen()) {
            		message = "applyChatting:" + requesterId;
            	}
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.error("메시지 전송 실패: {}", e.getMessage());
                sessions.remove(targetUserId);
            }
        } else {
            System.out.println("해당 사용자 세션이 없습니다: " + targetUserId);
        }
    }
    
    // 수락 요청을 처리하는 메서드
    public void handleAcceptance(String requesterId) {
        String acceptMessage = "채팅이 시작됩니다.";
        WebSocketSession requesterSession = sessions.get(requesterId);
        System.out.println(requesterSession);

        if (requesterSession != null && requesterSession.isOpen()) {
            try {
                requesterSession.sendMessage(new TextMessage(acceptMessage));
            } catch (IOException e) {
                logger.error("수락 메시지 전송 실패: {}", e.getMessage());
            }
        } else {
            System.out.println("요청자 세션이 없습니다: " + requesterId);
        }
    }

}
