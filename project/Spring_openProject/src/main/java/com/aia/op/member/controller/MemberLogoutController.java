package com.aia.op.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberLogoutController {

	@RequestMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 삭제
		System.out.println("로그아웃 !!");
		
		return "redirect:/"; // main
	}
}