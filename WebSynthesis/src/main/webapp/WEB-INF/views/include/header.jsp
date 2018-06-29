<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- JSTL 의 core 기능을 사용하기 위한 설정 : if, forEach, set 사용
	조건문 반복문 만드는거  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Synthesis</title>
<!-- 현재 기기의 너비에 맞추어서 출력을 하고
기본 크기는 1배 최대 크기도 1배 확대축소 못하게 하는 설정
모바일 웹 애플리케이션에서 주로 사용 -->
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<!-- 부트스트랩 스타일시트 파일 링크 설정
	contextpath는 절대 경로를 만들기 위해서 추가 -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
	<!-- IE 9 이전 버전에서 HTML5의 semantic 태그를 사용하기 위한 설정-->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
    <![endif]-->
</head>
<!-- jQuery 설정-->
<script
	src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
<body class="skin-blue sidebar-mini">
<div class="wrapper">
	<header class="main-header">
		<div class="page-header">
			<h1>Web Synthesis(웹 종합)</h1>
		</div>
	</header>
</div>

<aside class="main-sidebar">
	<section class="sidebar">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="#">메인</a></li>
			<li role="presentation"><a href="${pageContext.request.contextPath}/board/list">목록보기</a></li>
			<li role="presentation"><a href="${pageContext.request.contextPath}/board/register">게시물 쓰기</a></li>
			<li role="presentation"><a href="#">파싱그래프</a></li>
			<li role="presentation"><a href="#">Open API(지도,카톡)</a></li>
			<li role="presentation"><a href="#">웹소켓,네트워크</a></li>
				<c:if test="${user == null }">
		<li role="presentation"><a
href="${pageContext.request.contextPath}/user/register">회원가입</a></li>
<li role="presentation"><a
href="${pageContext.request.contextPath}/user/login">로그인</a></li>
</c:if>
<c:if test="${user !=null}">
	<img
		src="${pageContext.request.contextPath}/userimage/${user.image}"
		width="20" height="20" />
	<li role="presentation">${user.nickname}님</li>
	<li role="presentation"><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>

</c:if>
		</ul>
	</section>
</aside>

</body>
</html>