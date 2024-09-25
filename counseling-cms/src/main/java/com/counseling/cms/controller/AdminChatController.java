package com.counseling.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.utility.CookieUtility;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminChatController {
    
   
    
	@Autowired
	private JwtUtil jwtUtil;

    //test - 나중에 지울거임
	@GetMapping("/counselor/chatRoom")
	public String chattest(HttpServletRequest request, Model model) {
		String accessToken = CookieUtility.getCookie(request, "accessToken");
        String id = jwtUtil.extractUserId(accessToken);
        
        model.addAttribute("userId",id);
		
		return "/counselor/chattest";
	}
	
	@Controller
	public class ChatController {

	    @MessageMapping("/sendMessage") // 클라이언트가 이 엔드포인트로 메시지를 보냄
	    @SendTo("/topic/messages") // 메시지를 이 토픽을 구독하는 모든 클라이언트에게 보냄
	    public String sendMessage(String message) {
	        return message; // 클라이언트에게 메시지를 반환
	    }
	}

//    @GetMapping("/sendAcceptRequest")
//    public ResponseEntity<String> sendAcceptRequest(
//            @RequestParam String targetUserId,
//            @RequestParam String requesterId) {
//        socketHandlerUtility.sendAcceptRequest(targetUserId, requesterId);
//        return ResponseEntity.ok("수락 요청이 전송되었습니다.");
//    }
//    
//    // 수락 처리
//    @PostMapping("/acceptRequest")
//    public ResponseEntity<String> acceptRequest(
//            @RequestParam String requesterId,
//            @RequestParam String responderId) {
//        // 수락 메시지 처리
//        socketHandlerUtility.handleAcceptance(requesterId, responderId);
//        return ResponseEntity.ok("수락되었습니다.");
//    }

}
