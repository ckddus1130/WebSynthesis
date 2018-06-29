
package com.naver.ccy6451.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.ccy6451.domain.Board;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public void register(Board board) {
		sqlSession.insert("board.register",board);
	}
	//게시물 전체 목록을 가져오는 메소드
	//가져온 많은 데이터를 담아서 줘야하기 때문에 List<Board>
	public List<Board>list(){
		return sqlSession.selectList("board.list");
	}
	
	//글번호에 해당하는 데이터의 조회수를 1증가시키는 메소드
	public void updatecnt(int bno) {
		sqlSession.update("board.updatecnt",bno);
	}
	//글번호에 해당하는 데이터를 가져오는 메소드
	//게시글 수정할 때도 데이터를 가져와야 하니까 재사용이 되서
	
	public Board detail(int bno) {
		return sqlSession.selectOne("board.detail",bno);
	}
	
	//글번호에 해당하는 데이터의 수정을 처리하는 메소드
	public void update(Board board) {
		//잘 연결됬는지 한번 확인
		//System.out.println("dao 수행되는지~");
		sqlSession.update("board.update",board);
	}
}
