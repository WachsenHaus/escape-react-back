package com.react.escape.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.react.escape.notice.dto.NoticeDto;
import com.react.escape.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/notice/list")
	@ResponseBody
	public List<NoticeDto> getList_AJAX(HttpServletRequest request) {
		return noticeService.getList_AJAX(request);
	}
	@RequestMapping("/notice/ajax_listpage")
	@ResponseBody
	public Map<String, Object> getListPage_AJAX(HttpServletRequest request) {
		return noticeService.getListPage_AJAX(request);
	}
	
	@RequestMapping(value = "/notice/update_AJAX" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update_AJAX(HttpServletRequest request,NoticeDto dto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list", noticeService.getDetail_AJAX(request));
		return map;
	}

	@RequestMapping(value="/notice/doUpdate_AJAX", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doUpdate(HttpServletRequest request, NoticeDto dto) {
		Map<String, Object> map = new HashMap<String,Object>();
		boolean result = noticeService.updateContent_AJAX(request, dto);
		map.put("result",result);
		return map;
	}
	
	@RequestMapping("/notice/detail_AJAX")
	@ResponseBody
	public Map<String, Object> detail_AJAX(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", noticeService.getDetail_AJAX(request));
		return map;
	}
	@RequestMapping(value = "/notice/insert_AJAX", method = RequestMethod.POST)
	@ResponseBody
	public void insert_AJAX(NoticeDto dto,  HttpSession session) {
		//String aid=(String)session.getAttribute("aid");
		dto.setWriter("admin");
		noticeService.saveContent(dto);
//		mView.setViewName("notice/cheonho/insert");
	}
	@RequestMapping("/notice/delete")
	@ResponseBody
	public void delete_AJAX(int num, HttpServletRequest request) {
		//noticeService.deleteContent(num, request);
//		mView.setViewName("redirect:/notice/cheonho/list.do");
	}
	
}
