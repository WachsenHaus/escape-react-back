package com.react.escape.notice.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.react.escape.notice.dto.NoticeDto;

@Repository
public class NoticeDaoImpl implements NoticeDao{
	@Autowired
	private SqlSession session;

	@Override
	public List<NoticeDto> getList(NoticeDto dto) {
		return session.selectList("notice.getList", dto);
	}

	@Override
	public int getCount(NoticeDto dto) {
		return session.selectOne("notice.getCount_ajax", dto);
	}

	@Override
	public void addViewCount(int num) {
		session.update("notice.addViewCount", num);
	}

	@Override
	public NoticeDto getReview_AJAX(int num) {
		return session.selectOne("notice.getReview", num);
		}

	@Override
	public void update(NoticeDto dto) {
		session.update("notice.update", dto);		
	}
	@Override
	public void delete(int num) {
		session.delete("notice.delete", num);
	}

	@Override
	public void insert(NoticeDto dto) {
		session.insert("notice.insert", dto);

	}
	
}
