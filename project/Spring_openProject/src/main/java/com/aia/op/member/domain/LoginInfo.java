package com.aia.op.member.domain;

public class LoginInfo {

	private String memberid;
	private String membername;
	private String memberphoto;
	
	// 생성자
	public LoginInfo(String memberid, String membername, String memberphoto) {
		this.memberid = memberid;
		this.membername = membername;
		this.memberphoto = memberphoto;
	}

	// 로그인 정보가 바뀌면 안되니  only getter
	public String getMemberid() {
		return memberid;
	}

	public String getMembername() {
		return membername;
	}

	public String getMemberphoto() {
		return memberphoto;
	}

	@Override
	public String toString() {
		return "LoginInfo [memberid=" + memberid + ", membername=" + membername + ", memberphoto=" + memberphoto + "]";
	}
	
	
	
	
	
	
}
