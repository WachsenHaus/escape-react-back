package com.react.escape.login.service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.escape.login.dao.LoginDao;
import com.react.escape.login.dto.LoginDto;
import com.react.escape.notice.dao.NoticeDao;
import com.react.escape.notice.dto.NoticeDto;

@Service
public class LoginServiceImpl  implements LoginService {
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public boolean loginProcess_AJAX(LoginDto dto, HttpSession session) {
		boolean isValid=loginDao.isValid(dto);
		
		if(isValid) {
			session.setAttribute("aid", dto.getAid());
			return true;
		}else {
			return false;
		}
	}
}
