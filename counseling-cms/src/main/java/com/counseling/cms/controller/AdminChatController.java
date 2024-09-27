package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.utility.SocketHandlerUtility;

@Controller
public class AdminChatController {
    
    @Autowired
    private SocketHandlerUtility socketHandlerUtility;

    @GetMapping("/sendAcceptRequest")
    public ResponseEntity<String> sendAcceptRequest(
            @RequestParam String targetUserId,
            @RequestParam String requesterId) {
        socketHandlerUtility.sendAcceptRequest(targetUserId, requesterId);
        return ResponseEntity.ok("수락 요청이 전송되었습니다.");
    }
    
    // 수락 처리
    @PostMapping("/acceptRequest")
    public ResponseEntity<String> acceptRequest(
            @RequestParam String requesterId,
            @RequestParam String responderId) {
        // 수락 메시지 처리
        socketHandlerUtility.handleAcceptance(requesterId, responderId);
        return ResponseEntity.ok("수락되었습니다.");
    }

}
