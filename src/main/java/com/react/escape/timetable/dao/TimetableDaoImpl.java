package com.react.escape.timetable.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.react.escape.timetable.dto.ResInfoDto;
import com.react.escape.timetable.dto.TimetableDto;

@Repository
public class TimetableDaoImpl implements TimetableDao {

	@Autowired
	private SqlSession session;
		
	@Override
	public void resInsert(ResInfoDto dto) {
			session.insert("timetable.resInsert",dto);
	}

	@Override
	public void stateUpdate(ResInfoDto dto) {
		session.update("timetable.soldoutUpdate",dto);
		
	}

	@Override
	public String getResNumber(ResInfoDto dto) {
		String result = session.selectOne("timetable.getResNumber",dto);
		return result;
	}

	@Override
	public void TstatePosible(TimetableDto dto) {
		session.update("timetable.statePossible",dto);
		
	}

	@Override
	public void BstatePosible(TimetableDto dto) {
		session.update("timetable.statePossible2",dto);
			
	}

	@Override
	public List<TimetableDto> getTable(TimetableDto dto) {
		List<TimetableDto> list= session.selectList("timetable.getTable",dto);
		return list;
	}

	@Override
	public List<TimetableDto> getTable2(TimetableDto dto) {
		return session.selectList("timetable.getList",dto);
	}

}
