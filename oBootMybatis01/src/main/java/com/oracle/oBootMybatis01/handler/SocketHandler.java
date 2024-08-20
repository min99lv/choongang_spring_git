package com.oracle.oBootMybatis01.handler;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
	// 웹소켓 세션을 담아둘 맵
	HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
	// 웹소켓 세션 ID과 Member을 담아둘 맵
	HashMap<String, String> sessionUserMap = new HashMap<>();
	// 웹소켓 세션 ID과 Member을 담아둘 JSONObject
	JSONObject jsonUser = null;

	// 2. handleTextMessage 메소드는 메시지를 수신하면 실행
	// 메시지를 보낼때 사용하는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// Message 수신
		String msg = message.getPayload();
		System.out.println("SocketHandler handleTextMessage msg ===>"+msg);
		
		JSONObject jsonObj = jsonToObjectParser(msg);
		// type을 get하여 분기
		String msgType = (String) jsonObj.get("type");
		System.out.println("SocketHandler handleTextMessage msgType ===>"+msgType);
	}
	// 1. 웹소켓 연결이 되면 동작
	// 연결할 때 사용하는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SocketHandler afterConnectionEstablished start..");
		// 웹소켓 연결이 되면 동작
		super.afterConnectionEstablished(session);
		// 연결 소켓을 map에 등록
		sessionMap.put(session.getId(), session);
	}
	// 3. 웹소켓이 종료되면 동작
	// 연결을 끊을때 사용하는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		System.out.println("SocketHandler afterConnectionClosed start..");
		// 웹소켓 종료
		sessionMap.remove(session.getId());
		super.afterConnectionClosed(session, status);
	}

	// hadlerTextMessage 메소드에 Json파일이 들어오면 파싱해주는 함수를 추가
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
}
