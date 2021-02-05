<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.text_right {
		text-align: right;
	}
	
	.text_left {
		text-align: left;
	}
	.chattingBox {
		padding : 15px;
		border : 1px solid #AAA;
		margin: 10px 0;
	}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script> <!-- 웹소켓 객체 가져올 수 있음  -->

</head>
<body>

	<h1>Chatting Page (id: ${user})</h1>
	<br>
	<form>
		<div>
			<div>
				<input type="text" id="message" />
				<input type="submit" id="sendBtn" value="전송" />
			</div>
			<br>
			<div class="well" id="chatdata">
				<!-- User Session Info Hidden -->
				<input type="hidden" value='${user}' id="sessionuserid"> <!-- hidden -->
			</div>
		</div>
	</form>
</body>

<script>

	//websocket을 지정한 URL로 연결 
	var sock = new SockJS("<c:url value="/echo"/>"); 
	// 클라이언트는 브라우저를 통해서 처리. 자바스크립트 기반의 소켓이 필요! 
	// /echo 경로로 들어왔을 때 echohandler 실행 
	
	//websocket 서버에서 메시지를 보내면 자동으로 실행된다.
	sock.onmessage = onMessage;
	//websocket 과 연결을 끊고 싶을때 실행하는 메소드
	sock.onclose = onClose;
	
	
	$(document).ready(function(){ 
		
		$("form").submit(function() {
			console.log('send message...');
			sendMessage();
			
			$('#message').val(''); // 한번 보내면 포커스 비워주고 
			
			$('#message').focus(); // 다시 들어갈 수 있게 
			
			
			return false;
		});
		
	});
	
	$(function() {
	});

	function sendMessage() {
		//websocket으로 메시지를 보내기
		
		// 메세지 객체 
		var msg = {
			user : '${user}',
			to : 'jin', // 현재 페이지 작성자의 id를 작성 ( 나중에 수정 )  
			articleId : '${articleId}',
			articleOwner : '${articleOwner}',
			message : $("#message").val()
		};
		
		sock.send(JSON.stringify(msg));   // stringify : object to JSON 
	}

	// onMessage -> 서버쪽에서 받은 데이터 
	//evt 파라미터는 websocket이 보내준 데이터다. (전달받은 데이터)
	function onMessage(evt) { //변수 안에 function자체를 넣음.
		var data = evt.data; // 전달 받은 데이터
		
		//alert(data);
		
		msgData = JSON.parse(data); // 위에서 제이슨으로 바꿔서 받음. JSON -> 객체 
		
		// 사용했었던 
		var sessionid = null;
		var message = null;
		
		//current session id
		var currentuser_session = $('#sessionuserid').val();
		console.log('current session id: ' + currentuser_session);
		
		// 메세지 박스 
		var target = $('#chattingBox-1');
		
		if(target.length==0){
			$('<div id=\"chattingBox-1\" class=\"chattingBox\"></div>').html('<h3>${user} : 게시물 작성자-'+msgData.articleOwner+'</h3>').appendTo('body');
			$('#chattingBox-1').append('<hr>')
		}

			 
		// msgData = JSON.parse(data) 들어온 데이터 -> Message 클래스 형식 
		// 나와 상대방이 보낸 메세지를 구분하여 출력
		if (msgData.user == currentuser_session) { // 내가 보낸 메세지 -> 오른
			var printHTML = "<div class='well text_right'>"; 
			printHTML += "<div class='alert alert-info'>";
			printHTML += "<strong>[" + msgData.user + "] -> " + msgData.message
					+ "</strong>";
			printHTML += "</div>";
			printHTML += "</div>";

			$('#chattingBox-1').append(printHTML);
		} else { // 그 외 왼쪽 
			var printHTML = "<div class='well text_left'>";
			printHTML += "<div class='alert alert-warning'>";
			printHTML += "<strong>[" + msgData.user + "] -> " + msgData.message
					+ "</strong>";
			printHTML += "</div>";
			printHTML += "</div>";

			$('#chattingBox-1').append(printHTML);
		}

		console.log('chatting data: ' + data);

		/* sock.close(); */
	}

	function onClose(evt) {
		$("#data").append("연결 끊김");
	}
</script>
</html>