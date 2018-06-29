package com.naver.ccy6451.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.ccy6451.domain.User;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	//email 중복 체크를 위한 메소드 생성
	//email이 와야 되니까 매개변수에 email
	public String emailcheck(String email) {
		return sqlSession.selectOne("user.emailcheck",email);
	}
	
	//회원가입을 위한 메소드
	public void register(User user) {
		sqlSession.insert("user.register",user);
	}
	//로그인 처리를 위한 메소드
	public User login(String email) {
		return sqlSession.selectOne("user.login",email);
	
	}
}
