package com.react.escape.login.dto;

public class LoginDto {
	private String aid;
	private String apwd;

	
	public LoginDto() {}


	public LoginDto(String aid, String apwd) {
		super();
		this.aid = aid;
		this.apwd = apwd;
	}


	public String getAid() {
		return aid;
	}


	public void setAid(String aid) {
		this.aid = aid;
	}


	public String getApwd() {
		return apwd;
	}


	public void setApwd(String apwd) {
		this.apwd = apwd;
	}
}
