package com.react.escape.review.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.react.escape.review.dto.ReviewDto;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<ReviewDto> getList(ReviewDto reDto) {
		return session.selectList("review.getList", reDto);
	}

	@Override
	public int getCount(ReviewDto reDto) {
		return session.selectOne("review.getCount", reDto);
	}

	@Override
	public void addViewCount(int num) {
		session.update("review.addViewCount", num);
	}

	@Override
	public ReviewDto getReview(int num) {
		return session.selectOne("review.getReview", num);
	}

	@Override
	public ReviewDto getReview_AJAX(int num) {
		return session.selectOne("review.getReview", num);
	}

	@Override
	public ReviewDto getReviewKey(ReviewDto reDto) {
		return session.selectOne("review.getReivewKey", reDto);
	}

	@Override
	public void insertReview(ReviewDto reDto) {
		session.insert("review.insertReview", reDto);
		
	}

	@Override
	public void deleteReview(int num) {
		session.delete("review.deleteReview", num);
		
	}

	@Override
	public void updateReview(ReviewDto reDto) {
		session.update("review.updateReview", reDto);
		
	}

}
