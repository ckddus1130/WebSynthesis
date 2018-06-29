package com.naver.ccy6451.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.naver.ccy6451.domain.Board;

public interface BoardService {

	public void boardService(HttpServletRequest request);
	
	//게시판 글쓰기 BoardService 인터페이스에 메소드를 선언
	public void register(HttpServletRequest request);
	
	//게시판 전체목록 보기
	//여기엔 왜 HttpServletRequest 안하는지 물어보기
	//조회는 가져올필요없이 그냥 보면되서그런가?
	public List<Board>list();
	
	//게시물 상세보기를 위한 메소드
	public Board detail(HttpServletRequest request);
	//게시물 수정보기를 위한 메소드
	public Board updateView(HttpServletRequest request);
	
	//게시글 수정을 처리해줄 메소드를 선언
	public void update(HttpServletRequest request);
}
