package com.aia.op.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aia.op.member.domain.MemberEditRequest;
import com.aia.op.member.service.MemberEditService;

@Controller
@RequestMapping("/member/edit")
public class MemberEditController {
	
	@Autowired
	private MemberEditService editService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String editForm(
			//정보 공유
			@RequestParam("idx") int idx,
			Model model
			) {
		// Service -> MemberDao -> mapper (Member 객체 반환)
		// 반환  mapper -> MemberDao -> Service
		model.addAttribute("member", editService.getMember(idx));
		
		return "/member/editForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String editMember(
			MemberEditRequest editRequest,
			HttpServletRequest request,
			Model model //결과
			) {
		
		// Service -> MemberDao : update -> mapper -> int
		
		// System.out.println(editRequest);
		
		// 결과 edit 넘김
		model.addAttribute("result", editService.editMember(editRequest, request));  
		
		return "member/edit";
	}
	
	
	
	
	
	
}
