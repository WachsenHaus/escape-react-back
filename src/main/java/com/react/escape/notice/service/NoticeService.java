package com.react.escape.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.react.escape.notice.dto.NoticeDto;

public interface NoticeService {
	public List<NoticeDto> getList_AJAX(HttpServletRequest request);
	public Map<String, Object> getListPage_AJAX(HttpServletRequest request);
	public NoticeDto getDetail_AJAX(HttpServletRequest request);
	public boolean updateContent_AJAX(HttpServletRequest request, NoticeDto dto);

}
