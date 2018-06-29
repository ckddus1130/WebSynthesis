package com.naver.ccy6451.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.ccy6451.dao.BoardDao;
import com.naver.ccy6451.domain.Board;
import com.naver.ccy6451.domain.User;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public void boardService(HttpServletRequest request) {

	}
	@Override
	public void register(HttpServletRequest request) {
		//파라미터 읽기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();
		
		//email과 nickname은 왜 안하쥬? session에 저장되어있고
		//게시물 작성은 로그인 되었을 때만 가능하게 해야하기 때문에
		//이미 로그인되었을 때 email 과 pw nickname은 session에 저장되어서
		//없어지지않고 있어서 session에서 가져온다.???
		//로그인한 유저의 email 과 nickname은 session
		//의 user 속성에 있습니다.
		HttpSession session = request.getSession();
		//session에서 가져올 때는 형변환?
		//session은 바인딩된 객체중 name에 해당되는 객체값을 가져온다
		//없을 경우에는 null을 반환 반환값은 Object 형이므로 반드시 형변환을
		//해야 합니다 그래서 형변환
		//Type mismatch: cannot convert from Object to User
		User user =(User) session.getAttribute("user");
		
		String email = user.getEmail();
		String nickname = user.getNickname();
		
		//Dao의 메소드를 호출해야 하는 경우 Dao메소드의 파라미터를 생성
		//Dao를 왜부르는지 MVC 패턴의 순서를 생각해
		//View -> Controller -> Service -> Dao -> DB
		//Board의 객체를 만든 이유는 VO가 담는 공간이니까??
		Board board = new Board();
		board.setEmail(email);
		board.setIp(ip);
		board.setContent(content);
		board.setNickname(nickname);
		board.setTitle(title);
		
		System.out.println("파라미터" + board);
		//Dao 메소드 호출
		boardDao.register(board);
		//리턴할 결과를 만들어서 리턴 void라 없어서안하나??
		
		
	}
	@Override
	public List<Board> list() {
		return boardDao.list();
	}
	@Override
	public Board detail(HttpServletRequest request) {
		//파라미터 읽기
		String bno = request.getParameter("bno");
		//조회수 1증가시키는 메소드 호출
		//파라미터를 읽을 때 문자열로 읽었기 때문에 parseInt를해주세요
		boardDao.updatecnt(Integer.parseInt(bno));
		
		//데이터를 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}
	@Override
	public Board updateView(HttpServletRequest request) {
		//파라미터 읽기
		String bno = request.getParameter("bno");
		//데이터를 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));
	}
	@Override
	public void update(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String bno = request.getParameter("bno");
		//파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();
		
		//Dao의 메소드를 호출해야 하는 경우 Dao 메소드의
		//파라미터 생성
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setBno(Integer.parseInt(bno));
		board.setIp(ip);
		
		//Dao 메소드 호출
		boardDao.update(board);
		
	};
}
