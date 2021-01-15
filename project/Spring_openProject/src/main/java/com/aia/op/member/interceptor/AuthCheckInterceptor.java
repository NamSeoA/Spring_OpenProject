package com.aia.op.member.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		
		// 로그인 여부 확인하고 
		// 로그인 상태일 때 : return true (다음 interceptor로 넘어가거나 요청에 대한 처리)
		// 비로그인 상태 : return false , 로그인 페이지로 redirect
		
		HttpSession session = request.getSession(false); 
		// Session이 null일 때 그대로 유지, 유지하기 위해 false 전달
		
		if(session != null && session.getAttribute("loginInfo") != null) {
			return true;
		}
		
		// 그냥 false 처리하면 view가 나타나지 않음
		response.sendRedirect(request.getContextPath()+"/member/login");
		
		return false;
	}

	
}
