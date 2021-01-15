package com.aia.op.member.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aia.op.member.dao.MemberDao;
import com.aia.op.member.domain.Member;
import com.aia.op.member.domain.MemberEditRequest;

@Service
public class MemberEditService {
	
	private MemberDao dao; //sqlsessiontemplate안에있는 get이 이거 만들어줌
	
	@Autowired
	private SqlSessionTemplate template;
	
	public Member getMember(int idx) {
			dao = template.getMapper(MemberDao.class);
			return dao.selectMemberByIdx(idx);
	}
	
	public int editMember(
			MemberEditRequest editRequest,
			HttpServletRequest request 
			) {
	
		int result = 0;
		
		// MemberRegService
		// 웹 경로
		String uploadPath ="/fileupload/member";
		// 시스템의 실제 경로
		String saveDirPath = request.getSession().getServletContext().getRealPath(uploadPath);
		
		String newFileName = null; //파일이 있으면 지워주고 다시 만들고 null이면 그대로 하기 위함
		File newFile = null;
		
		// 1. 파일 처리 : 업로드할 새로운 파일이 존재하면 한다
		if(!editRequest.getUserPhoto().isEmpty()) {
			
			// oldFile 지워줘야함
			// 새로운 파일 이름
			newFileName = editRequest.getUserid()+System.currentTimeMillis();
			newFile = new File(saveDirPath, newFileName);
			
			// 파일 저장
			try {
				editRequest.getUserPhoto().transferTo(newFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// DB 보내기 전에 Dao와 연결하기 위한 member 객체 (수정할 데이터를 가지는 Member -> MemberDao)
		Member member = editRequest.getToMember();
		
		// 수정할 파일 이름 설정 (새로운 파일이 없니)
		if(newFileName == null) {
			member.setMemberphoto(editRequest.getOldPhoto()); // 이전 파일 이름
		} else { // 새로운 파일이 있으면
			member.setMemberphoto(newFileName);
		}
		
		try {
		// 2. DB : update 처리
		dao = template.getMapper(MemberDao.class);
		
		result = dao.updateMember(member);
		
		} catch (Exception e) {
			e.printStackTrace();
			
			// 저장된 파일을 삭제
			if(newFile != null && newFile.exists()) { // 존재여부 확인
				newFile.delete();
			}
		}
		
		// ******************
		// newfile 존재하고 old파일 존재하면 old파일 지움. 기본사진은 지우면 안됨
		if(newFile != null && !editRequest.getOldPhoto().equals("default.png")) {
			new File(saveDirPath,editRequest.getOldPhoto()).delete();
		}
		
		
		
		return result;
	}

}
