package com.naver.ccy6451.domain;

public class Board {
	private int bno; // 게시글번호 primary key(필수 , 유일)
	private String title;// 게시글제목 not null(필수)
	private String content; //게시글 내용
	private String regdate; //날짜
	private int readcnt; //조회수
	private String ip;  //ip 로그인 여부 판단하기 위해서?
	private String email; // 이메일 다른 테이블과 참조하기 위해
	private String nickname;
	//★날짜 및 시간을 출력할 변수★
	//오늘 작성한 글은 시간을 어제 이전에 작성된 글은 날짜 출력
	private String dispDate;
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDispDate() {
		return dispDate;
	}
	public void setDispDate(String dispDate) {
		this.dispDate = dispDate;
	}
	@Override
	public String toString() {
		return "Board [bno=" + bno + ", title=" + title + ", content=" + content + ", regdate=" + regdate + ", readcnt="
				+ readcnt + ", ip=" + ip + ", email=" + email + ", nickname=" + nickname + ", dispDate=" + dispDate
				+ "]";
	}
}
