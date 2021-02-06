package com.react.escape.timetable.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TimetableService {
	public void resInsert(HttpServletRequest request);
	public String getResnoApi(HttpServletRequest request);
	public Map<String, Object> getList(HttpServletRequest request);

}
