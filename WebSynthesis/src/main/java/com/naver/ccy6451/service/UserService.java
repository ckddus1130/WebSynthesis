package com.naver.ccy6451.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.naver.ccy6451.domain.User;

public interface UserService {

	//email 중복 체크를 위한 메소드
	//서비스의 파라미터는 3가지 형태
	//파라미터 각각을 파라미터로 만드는 경우 :@ RequestParam
	//파라미터를 전부 모아서 만드는 경우:  Command 객체
	//모든 경운데 동일한 파라미터를 사용할 때: HttpServletReqest 대신
	//MultipartHttpServletRequest로 변경
	
	public String emailcheck(HttpServletRequest request);
	
	//회원가입을 위한 메소드 작성
	//근데 파일이 없을 땐 HttpServletRequest 지만 파일이 있을 땐
	//MultipartHttpServletRequest를 사용
	public void register(MultipartHttpServletRequest request);
	
	//로그인 처리를 위한 메소드 작성
	public User login(HttpServletRequest request);

	public String address(String loc);
	
	//로그 파일을 읽어서 ip 별 트래픽 합계를 리턴해주는 메소드 선언
	//String에는 ip. Object에는 트래픽 합계를 저장해줄 거임
	public Map<String,Object> traffic();
	
	//강력범죄(배열로 따지면 ar[0])가 생활정도(ar[2,3,4,5,6])에 따라서 그래프가 어떻게
	//작성되는 지 첫번째 List는 강력범죄를 담고 그안의 List는 생활정도
	public List<List<Object>> crimeratio();
}
