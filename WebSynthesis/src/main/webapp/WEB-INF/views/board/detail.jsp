<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상세보기</title>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	<section class="content">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">상세보기</h3>
			</div>

			<div class="box-body">
				<div class="form-group">
					<label>제목</label> <input type="text" name="title"
						class="form-control" value="${vo.title}" readonly="readonly" />
				</div>
				<div class="form-group">
					<label>내용</label>
					<textarea name="content" rows="5" readonly="readonly"
						class="form-control">${vo.content}</textarea>
				</div>

				<div class="form-group">
					<label>작성자</label> <input type="text" class="form-control"
						value="${vo.nickname}" readonly="readonly" />
				</div>
			</div>
			<div class="box-footer">
				<button class="btn btn-success" id="mainbtn">메인</button>
				<c:if test="${user.email == vo.email}">
					<button class="btn btn-warning" id="updatebtn">수정</button>
					<button class="btn btn-danger" id="deletebtn">삭제</button>
				</c:if>
				<button class="btn btn-primary" id="listbtn">목록</button>
			</div>
			<!-- 로그인 한 유저와 작성자가 동일한 경우 -->
	<c:if test= "${user.email==vo.email}">
	 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<div id="dialog-confirm" title="파일삭제" style="display:none">
  <p>이 파일을 정말로 삭제하시겠습니까?</p>
</div>
	</c:if>
	
		</div>
	</section>
	<%@ include file="../include/footer.jsp"%>
	
	<script>
		//메인 버튼을 눌렀을 때 처리
		document.getElementById("mainbtn").addEventListener("click",
				function() {
					location.href = "../";
				});
		//목록 버튼을 눌렀을 때 처리
		document.getElementById("listbtn").addEventListener("click",
				function() {
					location.href = "list";
				});
		<c:if test = "${user.email == vo.email}">
		//삭제 버튼을 눌렀을 때 처리
		//삭제 버튼을 눌렀을 때
	document.getElementById("deletebtn").addEventListener("click",function(){
		//삭제 버튼을 눌렀을 때 처리
		$( "#dialog-confirm").dialog({
		      resizable: false, // 크기 조정
		      height: "auto", //높이 auto면 자동으로 크기 조정
		      width: 400,// 너비
		      modal: true,
		      buttons: {
		        "삭제": function() {
		          $( this ).dialog( "close" );
		          location.href="delete?bno=${vo.bno}";
		        },
		        "취소": function() {
		          $( this ).dialog( "close" );f
		        }
		      }
		    });
});
		//수정 버튼을 눌렀을 때 처리
		document.getElementById("updatebtn").addEventListener("click",
				function() {
					location.href = "update?bno=" + ${vo.bno};
				});
		</c:if>
	</script>
	
</body>
</html>