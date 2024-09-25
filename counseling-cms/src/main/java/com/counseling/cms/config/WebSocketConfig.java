package com.counseling.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.counseling.cms.interceptor.CustomHandshakeInterceptor;
import com.counseling.cms.utility.SocketHandlerUtility;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(socketHandlerUtility(), "/chat/rooms")
		.addInterceptors(handshake()).setAllowedOriginPatterns("*");
	}
	
	@Bean
	public HandshakeInterceptor handshake() {
		return new CustomHandshakeInterceptor();
	}
	
    @Bean
    public SocketHandlerUtility socketHandlerUtility() {
        return new SocketHandlerUtility();
    }

	
}
