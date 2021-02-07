package com.react.escape.confirm.dao;

import java.util.List;

import com.react.escape.confirm.dto.ConfirmDto;

public interface ConfirmDao {
	public List<ConfirmDto> getList(ConfirmDto dto);
	public void delete(ConfirmDto dto);
	public void update(ConfirmDto dto);
	public ConfirmDto getData(ConfirmDto dto);
}
