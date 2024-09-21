package com.counseling.cms.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AdminChatController {
    
    @MessageMapping("/admin/sendMessage")
    @SendTo("/counselor/messages")
    public String sendMessage(@Payload Map<String, String> message) {
        System.out.println("Handler invoked");
        
        if (message == null || message.get("text") == null) {
            System.out.println("Invalid message received");
            throw new IllegalArgumentException("Invalid message: text cannot be null");
        }
        
        System.out.println("Message payload: " + message);
        return "바보";
    }

}
