package com.counseling.cms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counseling.cms.config.WebSocketConfig;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.utility.CookieUtility;
import com.counseling.cms.utility.SocketHandlerUtility;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminChatController {
    
    @Autowired
    private SocketHandlerUtility socketHandlerUtility;
    
	@Autowired
	private JwtUtil jwtUtil;

    //test - 나중에 지울거임
	@GetMapping("/chat/chatRoom")
	public String chattest(HttpServletRequest request, Model model) {
		
		String accessToken = CookieUtility.getCookie(request, "accessToken");
        String id = jwtUtil.extractUserId(accessToken);
        model.addAttribute("userId",id);
		
		return "/counselor/chattest";
	}

}
