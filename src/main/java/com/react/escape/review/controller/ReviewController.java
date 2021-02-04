package com.react.escape.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.react.escape.review.dto.ReviewDto;
import com.react.escape.review.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	
	@RequestMapping("/review/ajax_list")
	@ResponseBody
	public List<ReviewDto> ajaxList(HttpServletRequest request)
	{	
		return reviewService.getList_AJAX(request);
	}
	@RequestMapping("/review/ajax_listpage")
	@ResponseBody
	public Map<String, Object> ajaxList2(HttpServletRequest request)
	{	
		return reviewService.getListPage_AJAX(request);
	}
	@RequestMapping(value = "/review/insert_AJAX", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insert_AJAX(ReviewDto reDto, ModelAndView mView, HttpSession session) {
		System.out.println(reDto);
		reviewService.saveContent(reDto);
		Map<String, Object> map=new HashMap<String, Object>();
		String success="isSuccess";
		map.put("success", success);
		return map;
	}
	
	
		//글 상세보기
	@RequestMapping("/review/review-detail_AJAX")
	@ResponseBody
	public Map<String, Object> detail_AJAX(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("list", reviewService.getDetail_AJAX(request));
		return map;
	}
	
		//삭제
	@RequestMapping("/review/private/delete_AJAX")
	@ResponseBody
	public void delete_AJAX(HttpServletRequest request, ReviewDto reviewDto) {	
		reviewService.deleteContent(request, reviewDto);
	}
	
	@RequestMapping(value="/review/private/update_AJAX", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update_AJAX(HttpServletRequest request, ReviewDto reDto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list",reviewService.getDetail_AJAX(request));
		return map;
	}
	
	@RequestMapping(value="/review/private/doUpdate_AJAX", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doUpdate(HttpServletRequest request, ReviewDto reDto, ModelAndView mView) {
		Map<String, Object> map = new HashMap<String,Object>();
		boolean result = reviewService.updateContent_AJAX(request, reDto);
		map.put("result",result);
		return map;
	}	
	@RequestMapping(value = "/review/imgUpload", method=RequestMethod.POST)
	public void saveImg(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile) {
		reviewService.saveImg(request, response,multiFile);
	}
	
//	//글 목록
//	@RequestMapping("/review/list")
//	public ModelAndView getList(HttpServletRequest request, ModelAndView mView) {
//		reviewService.getList(request);
//		mView.setViewName("review/list");
//		
//		return mView;
//	}
//	
//	//글 상세보기
//	@RequestMapping("/review/review-detail")
//	public ModelAndView detail(HttpServletRequest request, ModelAndView mView) {
//		reviewService.getDetail(request);
//		mView.setViewName("review/review-detail");
//		
//		return mView;
//	}
//	
	//글쓰기
	@RequestMapping("/review/insert-form")
	public ModelAndView insertForm(ModelAndView mView) {

		mView.setViewName("review/insert-form");
		return mView;
	}
	
	@RequestMapping(value = "/review/insert", method=RequestMethod.POST)
	public ModelAndView insert(ReviewDto reDto, ModelAndView mView, HttpSession session) {	
		reviewService.saveContent(reDto);
		mView.setViewName("review/insert");
		
		return mView;
	}

//	//삭제
//	@RequestMapping("/review/private/delete")
//	public ModelAndView delete(HttpServletRequest request, ModelAndView mView, ReviewDto reviewDto) {
//		
//		reviewService.deleteContent(request, reviewDto);
//		mView.setViewName("redirect:/review/list.do");
//		
//		return mView;
//	}
//	
//	
//	//수정
//	@RequestMapping("/review/private/update-form")
//	public ModelAndView updateform(HttpServletRequest request, ModelAndView mView) {
//		reviewService.getDetail(request);
//		mView.setViewName("review/private/update-form");
//		
//		return mView;
//	}
//	
//	@RequestMapping(value="/review/private/update", method=RequestMethod.POST)
//	public ModelAndView update(HttpServletRequest request, ReviewDto reDto, ModelAndView mView) {
//		reviewService.updateContent(request, reDto);
//		mView.setViewName("review/private/update");
//		
//		return mView;
//	}
	
}
