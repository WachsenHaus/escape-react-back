package com.react.escape.login.dao;

import com.react.escape.login.dto.LoginDto;

public interface LoginDao {
	public boolean isValid(LoginDto dto);
}
