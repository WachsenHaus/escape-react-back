package com.react.escape.confirm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.react.escape.confirm.dto.ConfirmDto;
import com.react.escape.confirm.service.ConfirmService;

@Controller
public class ConfirmController {

	@Autowired
	private ConfirmService confirmService;
	//2021-01-14
	@RequestMapping("/confirm/confirmApi")
	@ResponseBody
	public Map<String,Object> confirmApi(@ModelAttribute ConfirmDto dto, HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<ConfirmDto> list = confirmService.getList(dto,request);
		
		map.put("list", list);
		return map;
	}
	@RequestMapping("/confirm/deleteApi")
	@ResponseBody
	public Map<String,Object> delete(ConfirmDto dto, HttpServletRequest request) throws IOException {	
		List<ConfirmDto> list = confirmService.getList(dto, request);
		Map<String, Object> map=new HashMap<String, Object>();
		String result = "false";	
		if(!list.isEmpty()) {
			confirmService.deleteConfirm(dto);
			result = "true";
		}
		map.put("result", result);
		return map;
	}
	
}
