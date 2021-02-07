package com.react.escape.confirm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.escape.confirm.dto.ConfirmDto;
import com.react.escape.confirm.dao.ConfirmDao;

@Service
public class ConfirmServiceImpl implements ConfirmService {
	
	@Autowired
	private ConfirmDao confirmDao;
	
	@Override
	public List<ConfirmDto> getList(ConfirmDto dto, HttpServletRequest request) {
		List<ConfirmDto> list = confirmDao.getList(dto);
		request.setAttribute("list", list);
		return list;
	}

	@Override
	public void deleteConfirm(ConfirmDto dto) {
		List<ConfirmDto> list = confirmDao.getList(dto);
		confirmDao.update(confirmDao.getData(dto));
		confirmDao.delete(dto);
	}

}
