package com.react.escape.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.react.escape.login.dto.LoginDto;
import com.react.escape.login.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	
	@RequestMapping(value = "/login/login_AJAX", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login_AJAX(LoginDto dto, HttpSession session) {
		Map<String, Object> map=new HashMap<String, Object>();
		String result = loginService.loginProcess_AJAX(dto,  session) == true ? "isSuccess":"isFail";
		if(result == "isFail")
		{
			map.put("success","isFail");	
			return map;
		}
		String aid=(String)session.getAttribute("aid");
		if(aid != null) {
			map.put("success",aid);
		}else { 
			map.put("success","isFail");
		}
		return map;
	}
	
	
	//미구현
	@RequestMapping("/login/logout_AJAX")
	public void logout_AJAX(HttpSession session) {
		session.invalidate();
	}	
	
}


