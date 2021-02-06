package com.react.escape.timetable.dao;

import java.util.List;

import com.react.escape.timetable.dto.ResInfoDto;
import com.react.escape.timetable.dto.TimetableDto;

public interface TimetableDao {
	public void resInsert(ResInfoDto dto);
	public void stateUpdate(ResInfoDto dto);
	public String getResNumber(ResInfoDto dto);

	public void TstatePosible(TimetableDto dto);
	public void BstatePosible(TimetableDto dto);
	public List<TimetableDto> getTable(TimetableDto dto);
	public List<TimetableDto> getTable2(TimetableDto dto);

}
