package com.react.escape.confirm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.react.escape.confirm.dto.ConfirmDto;

public interface ConfirmService {
	public List<ConfirmDto> getList(ConfirmDto dto, HttpServletRequest request);
	public void deleteConfirm(ConfirmDto dto);
}
