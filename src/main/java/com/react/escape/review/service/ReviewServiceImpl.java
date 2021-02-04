package com.react.escape.review.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.react.escape.review.dao.ReviewDao;
import com.react.escape.review.dto.ReviewDto;
import com.google.gson.JsonObject;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;
	
	
	
	//한 페이지당 row수
	final int PAGE_ROW_COUNT=10;
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public List<ReviewDto> getList_AJAX(HttpServletRequest request) {
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
				ReviewDto dto=new ReviewDto();
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
				List<ReviewDto> list = reviewDao.getList(dto);
				return list;		
	}
	@Override
	public void deleteContent(HttpServletRequest request, ReviewDto reviewDto) {
		
		//DB에 저장된 pwd
		ReviewDto dto = reviewDao.getReview(reviewDto.getNum());
		String encodedPwd = dto.getPwd();
		
		//입력받은 pwd
		String getPwd = (String)request.getParameter("pwd");
		

		// 비밀번호 일치여부 확인 후 수정 혹은 Exception
		if(BCrypt.checkpw(getPwd, encodedPwd)) {
			reviewDao.deleteReview(dto.getNum());
		}
	}

	@Override
	public Map<String, Object> getListPage_AJAX(HttpServletRequest request) {
		Map<String, Object> list = new HashMap<>();
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
	
	
			//검색 키워드와 startRowNum, endRowNum 을 담을 FileDto 객체 생성
			ReviewDto dto=new ReviewDto();
			dto.setStartRowNum(startRowNum);
			dto.setEndRowNum(endRowNum);
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
			int totalRow=reviewDao.getCount(dto);
			
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
	public void saveContent(ReviewDto reviewDto) {
		//비밀번호 암호화
		String getPwd = reviewDto.getPwd();
		
		//비밀번호 받아서 인코딩 하고 dto에 다시 넣어준다.
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPwd = encoder.encode(getPwd);

		reviewDto.setPwd(encodedPwd);
		
		reviewDao.insertReview(reviewDto);
		
	}

	@Override
	public ReviewDto getDetail_AJAX(HttpServletRequest request) {
		int num=Integer.parseInt(request.getParameter("num"));
		//조회수
		reviewDao.addViewCount(num);
		
		//카페글 목록 얻어오기
		return reviewDao.getReview_AJAX(num);	
		}

	@Override
	public boolean updateContent_AJAX(HttpServletRequest request, ReviewDto reviewDto) {
		ReviewDto dto = reviewDao.getReview(reviewDto.getNum());
		String encodedPwd = dto.getPwd();
		boolean result = false;
		String inputPwd = (String)request.getParameter("pwd");
		
		if(BCrypt.checkpw(inputPwd, encodedPwd)) {
			reviewDao.updateReview(reviewDto);
			result = true;
		}
		return result;
	}

	@Override
	public void saveImg(HttpServletRequest request, HttpServletResponse response,
			MultipartHttpServletRequest multiFile) {
		//멀티파이서블릿리퀘이트에는 파일정보가 담겨져있다.
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		int state = 0;
		String fileName ="";
		String fileUrl = "";
		//파일을 할당한다.
		MultipartFile file = multiFile.getFile("upload");

		if(file != null){
			if(file.getSize() > 0 && !StringUtils.isBlank(file.getName())){
				if(file.getContentType().toLowerCase().startsWith("image/")){
					try{
						//여기서 file.getName초기값은 그냥 upload이다.
						fileName = file.getName();
						byte[] bytes = file.getBytes();
						//img폴더에 업로드한다.
						//C:\Users\Younghoon\Desktop\eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\clone_clien\img
						String uploadPath = request.getServletContext().getRealPath("/img");
						File uploadFile = new File(uploadPath);
						if(!uploadFile.exists()){
							uploadFile.mkdirs();
						}
						//랜덤값을 파일이름에 붙임.
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						//파일쓰기
						out = new FileOutputStream(new File(uploadPath));
                        out.write(bytes);
                        
                        printWriter = response.getWriter();
                        response.setContentType("text/html");
                        fileUrl = "http://localhost:" + request.getServerPort() + request.getContextPath() + "/img/" + fileName;
                        state = 1;
                     }catch(IOException e){
                    	 state = 0;
                        e.printStackTrace();
                    }finally{
                        // json 데이터로 등록
                        // {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
                        // 이런 형태로 리턴이 나가야함.
                        json.addProperty("uploaded", state);
                        json.addProperty("fileName", fileName);
                        json.addProperty("url", fileUrl);
                        //이런식의 json이다
                        //{"uploaded":1,"fileName":"a131250a-7634-4a32-ad98-2ed10f25b2b5","url":"/clien/img/a131250a-7634-4a32-ad98-2ed10f25b2b5"}
                        System.out.println(json);
                        printWriter.println(json);
                        if(out != null){
                            try {
								out.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }
                        if(printWriter != null){
                            printWriter.close();
                        }		
					}
				}
			}
		}		
	}
}