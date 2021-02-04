package com.react.escape.review.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.react.escape.review.dto.ReviewDto;

public interface ReviewService {
	public List<ReviewDto> getList_AJAX(HttpServletRequest request);
	public Map<String, Object> getListPage_AJAX(HttpServletRequest request);
	public ReviewDto getDetail_AJAX(HttpServletRequest request);
	public boolean updateContent_AJAX(HttpServletRequest request, ReviewDto reviewDto);
	public void saveImg(HttpServletRequest request, HttpServletResponse response,MultipartHttpServletRequest multiFile);
	public void saveContent(ReviewDto reviewDto);
	public void deleteContent(HttpServletRequest request, ReviewDto reviewDto);

}
