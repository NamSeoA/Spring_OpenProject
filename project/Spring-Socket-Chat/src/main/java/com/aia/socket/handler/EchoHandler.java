package com.aia.socket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.aia.socket.domain.Message;
import com.google.gson.Gson;

public class EchoHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	
	// List에도 담을 수 있고 Map에도 담을 수 있다
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	//private Map<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

	// client가 접속하면 afterConnectionEstablished 메서드 호출
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		String chatMember = (String) session.getAttributes().get("user"); // 누가 보낸건지

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + chatMember);

		sessionList.add(session);
		//sessionMap.put(chatMember, session);

		logger.info("{} 연결되었습니다.", session.getId()+":"+chatMember);

		System.out.println("체팅 참여자 : " + chatMember);
	}

	// client가 메세지를 보내면  handleTextMessage 메서드 호출 (textmessage JSON형식)
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String chatMember = (String) session.getAttributes().get("user");
		
		logger.info("{}로 부터 {}를 전달 받았습니다.", chatMember, message.getPayload());
		Gson gson = new Gson(); // client에서 json형식으로 보냄
		Message msg = gson.fromJson(message.getPayload(), Message.class); // Json -> JAVA object로
		
		System.out.println(msg);
		

		// 전달 메시지
		TextMessage sendMsg = new TextMessage(gson.toJson(msg)); //메세지 객체 -> Json 형식으로
		
		for (WebSocketSession webSocketSession : sessionList) {
			// 상대방에게 메시지 전달
			webSocketSession.sendMessage(sendMsg);
		}
		// 자신에게 메시지 전달
		//session.sendMessage(sendMsg); 
		
	}

	// client가 웹페이지 벗어나거나 채팅을 나가면 afterConnectionClosed 메서드 호출 (접속 종료되면 호출)
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		String chatMember = (String) session.getAttributes().get("user");
		sessionList.remove(session);
		logger.info("{} 연결이 끊김", session.getId()+chatMember);
		System.out.println("체팅 퇴장 : " + chatMember);
	}

}
