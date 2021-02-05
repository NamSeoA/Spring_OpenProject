package com.aia.socket.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChattingController { // 채팅 화면

	@RequestMapping("/chatting") // chatting view 페이지로 넘겨줌 (웹페이지)
	public ModelAndView chat(
			
			ModelAndView mv,				 // 데이터를 뷰페이지쪽으로 같이 전송하기 위함 
			@RequestParam("uid") String uid, // 테스트용 (실제 로그인한 사용자의 아이디를 불러와야함) 
			HttpSession session
			) {
		
		mv.setViewName("chat/chat"); // chat 폴더/ chat.jsp 
		mv.addObject("user", uid);
		mv.addObject("articleId", "12345");
		mv.addObject("articleOwner", "jin");
		
		session.setAttribute("user", uid); // 테스트 하기 위한 세션 만듬 (고쳐야함)
		
		return mv;
	}
	
}
