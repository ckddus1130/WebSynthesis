<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 현재 파일 위치에서 include 디렉토리의 header.jsp파일의 코드를
가져와서 삽입 -->
<%@ include file="include/header.jsp"%>
<section class="content">
	<div class="box">
		<div class="box-header with-border" id="address"></div>
		<c:if test="${user==null}">
			<div class="box-header with-border">
				<a href="user/login"><h3 class="box-title">로그인</h3></a>
			</div>

			<div class="box-header with-border">
				<a href="user/register"><h3 class="box-title">회원가입</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="board/register"><h3 class="box-title">게시글 작성</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="board/list"><h3 class="box-title">게시글 보기</h3></a>
			</div>
		</c:if>

		<c:if test="${user!=null}">
			<div class="box-header with-border">
				<a href="user/logout"><h3 class="box-title">로그아웃</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="board/register"><h3 class="box-title">게시글 작성</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="board/list"><h3 class="box-title">게시글 보기</h3></a>
			</div>
		</c:if>
	</div>
</section>
<%@ include file="include/footer.jsp"%>

<div id="map" style="width: 500px; height: 400px;"></div>
<div id="chartdiv" style="width: 500px; height: 400px;"></div>
<!-- 영역 생성 -->
<div id="crimechart" style="width: 500px; height: 400px;"></div>

<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b61b3413fbc16ccf7b6fa3a57f49fe92"></script>

<script>
	//10초마다 한번씩 동작하는 타이머
	setInterval(function() {

		//현재 위치를 조사하는 함수
		//아래에 직접 위도와 경도를 찾아서 입력해도 되고 현재 위치를 받아오고싶을 때는 아래와 같이
		//코드를 작성해주면 현재 위치가 작성됩니다.
		navigator.geolocation.getCurrentPosition(function(position) {

			//전송해 줄 파라미터 만들기
			loc = position.coords.latitude + "-" + position.coords.longitude;
			var container = document.getElementById('map');
			var options = {
				center : new daum.maps.LatLng(position.coords.latitude,
						position.coords.longitude),
				level : 3
			};

			var map = new daum.maps.Map(container, options);

		});
	}, 5000);
</script>
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="jqplot/excanvas.js"></script><![endif]-->
<script src="jqplot/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="jqplot/plugins/jqplot.barRenderer.js"></script>
<script type="text/javascript" src="jqplot/plugins/jqplot.pieRenderer.js"></script>
<script type="text/javascript" src="jqplot/plugins/jqplot.categoryAxisRenderer.js"></script>
<script type="text/javascript" src="jqplot/plugins/jqplot.pointLabels.js"></script>
<link rel="jqplot/stylesheet" href="jqplot/jquery.jqplot.min.css" />

<script>
//항상 ajax 요청할 대는 &.ajax
//항상 아래와 같은 방식으로 데이터를 가져오는 지 확인을하고
//작업을 해주세요 중간중간 테스트를 하면서 하면 어디서 오류 에러가 났는지
//쉽게 확인이 가능합니다.
/*
$.ajax({
	url:"traffic",
	data:{},
	dataType:"json",
	success:function(data){
			alert(data);
	}

});*/
$.ajax({
	url:"traffic",
	data:{},
	dataType:"json",
	success:function(data){
		//alert(data) 안되서 다시 작업 여기까지는 옴
		$.jqplot.config.enablePlugins = true;
		var s1 = new Array();
        var ticks = new Array();
        for(key in data){
        	ticks.push(key);
        	s1.push(data[key]);
}
        
        plot1 = $.jqplot('chartdiv', [s1], {
            // Only animate if we're not using excanvas (not in IE 7 or IE 8)..
            animate: !$.jqplot.use_excanvas,
            seriesDefaults:{
                renderer:$.jqplot.BarRenderer,
                pointLabels: { show: true }
            },
            axes: {
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                },
                yaxis:{
                	
                }
            },
            highlighter: { show: false }
        });
        $('#chartdiv').bind('jqplotDataClick', 
	            function (ev, seriesIndex, pointIndex, data) {
	                $('#info1').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
	            }
	        );

	}
});

</script>
<script type="text/javascript" src="jqplot/plugins/jqplot.bubbleRenderer.js"></script>

<script>
	$.ajax({
		url:"crimeratio",
		data:{},
		dataType:"json",
			success:function(data){
				 var arr = [[11, 123, 1236, "Acura"], [45, 92, 1067, "Alfa Romeo"], 
					    [24, 104, 1176, "AM General"], [50, 23, 610, "Aston Martin Lagonda"], 
					    [18, 17, 539, "Audi"], [7, 89, 864, "BMW"], [2, 13, 1026, "Bugatti"]];
					     
					    plot1 = $.jqplot('crimechart',[arr],{
					        title: 'Transparent Bubbles',
					        seriesDefaults:{
					            renderer: $.jqplot.BubbleRenderer,
					            rendererOptions: {
					                bubbleAlpha: 0.6,
					                highlightAlpha: 0.8
					            },
					            shadow: true,
					            shadowAlpha: 0.05
					        }
					    });    
			}
	});


</script>
