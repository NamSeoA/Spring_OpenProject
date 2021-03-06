package com.aia.op.member.domain;

public class SearchParam {
	
	private int p; // 페이징 번호는 1보다 작으면 안됨
	private String searchType;
	private String keyword;
	
	
	
	public SearchParam(int p, String searchType, String keyword) {
		this.p = p;
		this.searchType = searchType;
		this.keyword = keyword;
		
		if(this.p<1) { 
			this.p = 1; //초기화
		}
	}

	public SearchParam() {
		this.p = 1;
	}

	//Getter Setter
	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "SearchParam [p=" + p + ", searchType=" + searchType + ", keyword=" + keyword + "]";
	}
	
	
	
	
}
