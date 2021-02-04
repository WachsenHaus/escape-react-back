package com.react.escape.notice.dao;

import java.util.List;

import com.react.escape.notice.dto.NoticeDto;

public interface NoticeDao {
	public List<NoticeDto> getList(NoticeDto dto);
	public int getCount(NoticeDto dto);
	public void addViewCount(int num);
	public NoticeDto getReview_AJAX(int num);
	public void update(NoticeDto dto);
	public void delete(int num);

}
