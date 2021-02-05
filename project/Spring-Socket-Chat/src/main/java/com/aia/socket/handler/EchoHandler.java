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

	// Logger : EchoHandler.class 현재의 클래스를 대상으로 log를 수집하겠다는 의미
	// 로그를 수집할 클래스(EchoHandler) 클래스에 변수를 선언
	// private : 외부에서 로그를 가로채지 못하게 하기 위함
	// static final :  로그 내용이 바뀌지 않으므로
	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	
	// List에도 담을 수 있고 Map에도 담을 수 있다
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	//private Map<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

	// client가 접속하면 afterConnectionEstablished 메서드 호출 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		String chatMember = (String) session.getAttributes().get("user"); // 세션에 있는 세션들! 정보를 얻을 수 있음  (현재 보낸 사람 정보)

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + chatMember);

		sessionList.add(session);
		//sessionMap.put(chatMember, session);

		logger.info("{} 연결되었습니다.", session.getId()+":"+chatMember);
		System.out.println("채팅 참여자 : " + chatMember);
		// INFO : com.aia.socket.handler.EchoHandler - mlf15vf4:seoa 연결되었습니다.
		// 채팅 참여자 : seoa
	}

	// client가 메세지를 보내면  handleTextMessage 메서드 호출 (textmessage -  JSON형식)
	// client가 EchoHandler쪽으로 메세지 보내줌 (누가 보냈는지에 대한 정보는 websocketsession에 있고  메세지 받음)
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		// 메세지 받기  ---------------------- 
		String chatMember = (String) session.getAttributes().get("user"); // 현재 세션 유저 
		
		logger.info("{}로 부터 {}를 전달 받았습니다.", chatMember, message.getPayload()); // 메세지 받아
		
		Gson gson = new Gson(); // client에서 Json형식으로 보냄
		Message msg = gson.fromJson(message.getPayload(), Message.class); // Json -> JAVA object
		
		System.out.println(msg);
		

		// 전달 메시지 -----------------------
		TextMessage sendMsg = new TextMessage(gson.toJson(msg)); // 메세지 객체 -> Json 형식으로
		 
		for (WebSocketSession webSocketSession : sessionList) { // 전체에게 
			// 상대방에게 메시지 전달
			webSocketSession.sendMessage(sendMsg); // 바꿔놓은 Json 전달 
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
