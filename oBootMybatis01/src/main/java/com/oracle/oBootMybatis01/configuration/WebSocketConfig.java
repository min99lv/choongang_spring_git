package com.oracle.oBootMybatis01.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.oracle.oBootMybatis01.handler.SocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	SocketHandler socketHandler;
	@Override 
											// registry소켓이 발생하면 등록될 장소
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 누군가 URL / chating --> socketHandler 발동 -> 컨트롤러가 아니지만 소켓세계에서는 like 컨트롤러
		registry.addHandler(socketHandler, "/chating");
		
	}

}
