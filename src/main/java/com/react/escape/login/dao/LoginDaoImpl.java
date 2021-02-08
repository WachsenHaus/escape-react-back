package com.react.escape.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.react.escape.login.dao.LoginDao;
import com.react.escape.login.dto.LoginDto;

@Repository
public class LoginDaoImpl implements LoginDao {
	@Autowired
	private SqlSession session;
	@Override
	public boolean isValid(LoginDto dto) {
		String aid=session.selectOne("login.isValid",dto);
		if(aid==null) {
			return false;
		}else {
			return true;
		}
	}
	

}
