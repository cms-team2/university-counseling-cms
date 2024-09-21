package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminChatController {

	@MessageMapping("/admin/sendMessage")
	@SendTo("/counselor/messages")
	public String sendMessage(@Payload Map<String, String> message) {
		System.out.println("5555");
		
	    if (message == null || message.get("text") == null) {
	        throw new IllegalArgumentException("Invalid message: text cannot be null");
	    }
	    
	    System.out.println("123");
	    System.out.println("Received message: " + message.get("text"));
	    return message.get("text");
	}
}
