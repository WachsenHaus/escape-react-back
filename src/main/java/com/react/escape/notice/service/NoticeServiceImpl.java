package com.react.escape.notice.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.escape.notice.dao.NoticeDao;
import com.react.escape.notice.dto.NoticeDto;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	final int PAGE_ROW_COUNT=30;
	final int PAGE_DISPLAY_COUNT=5;
	
	
	
	@Override
	public List<NoticeDto> getList_AJAX(HttpServletRequest request) {
//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		/*
			검색 키워드에 관련된 처리 
		*/
		String keyword=request.getParameter("keyword"); //검색 키워드
		String condition=request.getParameter("condition"); //검색 조건
		if(keyword==null){//전달된 키워드가 없다면 
			keyword=""; //빈 문자열을 넣어준다. 
			condition="";
		}
		//인코딩된 키워드를 미리 만들어 둔다. 
		String encodedK=URLEncoder.encode(keyword);
		
		//검색 키워드와 startRowNum, endRowNum 을 담을 FileDto 객체 생성
		NoticeDto dto=new NoticeDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){ //만일 키워드가 넘어온다면 
			if(condition.equals("title_content")){
				//검색 키워드를 FileDto 객체의 필드에 담는다. 
				dto.setTitle(keyword);
				dto.setContent(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
	
		//카페글 목록 얻어오기
		List<NoticeDto> list = noticeDao.getList(dto);
		return list;	
	}

	@Override
	public Map<String, Object> getListPage_AJAX(HttpServletRequest request) {
	Map<String, Object> list = new HashMap<>();
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		String branch=request.getParameter("branch");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;


		//검색 키워드와 startRowNum, endRowNum 을 담을 FileDto 객체 생성
		NoticeDto dto=new NoticeDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		dto.setBranch(branch);
		//키워드 검색
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		
		if(keyword==null){
			keyword="";
			condition="";
		}
		
		String encodedK=URLEncoder.encode(keyword);
		

		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){
			if(condition.equals("title_content")){
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}

		//전체 row 의 갯수 
		int totalRow=noticeDao.getCount(dto);
		
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		list.put("startPageNum", startPageNum);
		list.put("endPageNum", endPageNum);
		list.put("pageNum", pageNum);
		list.put("totalPageCount", totalPageCount);
		
		return list;
	}

	@Override
	public NoticeDto getDetail_AJAX(HttpServletRequest request) {
		int num=Integer.parseInt(request.getParameter("num"));
		//조회수
		noticeDao.addViewCount(num);
		//카페글 목록 얻어오기
		return noticeDao.getReview_AJAX(num);
	}

	@Override
	public boolean updateContent_AJAX(HttpServletRequest request, NoticeDto dto) {
		boolean result = false;
		try {
			noticeDao.update(dto);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

}
