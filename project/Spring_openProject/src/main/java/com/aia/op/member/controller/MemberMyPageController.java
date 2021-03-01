package com.aia.op.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aia.op.member.service.RedisService;

@Controller
@RequestMapping("/mypage")
public class MemberMyPageController { 
	// 컨트롤러 만들 때 기본이 되는 페이지를 만드는 것이 좋다.
	
	@Autowired
	private RedisService redisService;
	
	//@RequestMapping("/mypage1") // /op/mypage/mypage1
	//public String myPage1() {
	//	return "/member/mypage";
	//}
	
	// session 값 받는 테스트용 
	@RequestMapping("/mypage1")
	public String myPage1(@CookieValue("JSESSIONID") String sessionId) {

		System.out.println(redisService.getUserInformation(sessionId));

		return "member/mypage";
	}

	
	@RequestMapping("/mypage2") // /op/mypage/mypage2
	public String myPage2() {
		return "/member/mypage";
	}
	
	@RequestMapping("/mypage3") // /op/mypage/mypage3
	public String myPage3() {
		
		String str = null;
		str.charAt(1);
		
		return "/member/mypage";
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handlNullPointerException(NullPointerException e) {
		e.printStackTrace();
		return "error/nullPointer";
	}
}
