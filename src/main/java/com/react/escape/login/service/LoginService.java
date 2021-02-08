package com.react.escape.login.service;

import javax.servlet.http.HttpSession;

import com.react.escape.login.dto.LoginDto;

public interface LoginService {
	public boolean loginProcess_AJAX(LoginDto dto, HttpSession session);

}
