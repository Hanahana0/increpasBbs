<%@page import="mybatis.vo.CommVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#bbs table {
	    width:580px;
	    margin-left:10px;
	    border:1px solid black;
	    border-collapse:collapse;
	    font-size:14px;
	    
	}
	
	#bbs table caption {
	    font-size:20px;
	    font-weight:bold;
	    margin-bottom:10px;
	}
	
	#bbs table th,#bbs table td {
	    text-align:center;
	    border:1px solid black;
	    padding:4px 10px;
	}
	
	.no {width:15%}
	.subject {width:30%}
	.writer {width:20%}
	.reg {width:20%}
	.hit {width:15%}
	.title{background:lightsteelblue}
	
	.odd {background:silver}
	
	/* paging */
	
	table tfoot ol.paging {
	    list-style:none;
	    
	}
	
	table tfoot ol.paging li {
	    float:left;
	    margin-right:8px;
	    
	}
	
	table tfoot ol.paging li a {
	    display:block;
	    padding:3px 7px;
	    border:1px solid #00B3DC;
	    color:#2f313e;
	    font-weight:bold;
	    text-decoration: none;
	}
	
	table tfoot ol.paging li a:hover {
	    background:#00B3DC;
	    color:white;
	    font-weight:bold;
	}
	
	.disable {
	    padding:3px 7px;
	    border:1px solid silver;
	    color:silver;
	}
	
	.now {
	   padding:3px 7px;
	    border:1px solid #ff4aa5;
	    background:#ff4aa5;
	    color:white;
	    font-weight:bold;
	}
	#search_wrap{
		padding: 15px;
		margin: 0 auto;
		border: 1px solid red;
	}
	#search_wrap input[type=text]{
		padding: 10px;
		width: 400px;
		border: 1px solid #ababab;
		border-radius: 15px;
		font-size: 0.85em;
	}
	#search_wrap input[type=button] {
		margin-left: 15px;
		padding: 7px;
		font-size: 12;
		font-size: 0.85em;
		border: 1px solid #acacac;
		border-radius: 5px;
		text-align: 9999;
		
	}

	
</style>
</head>
<body>
	
	<div id="bbs">
		<table summary="게시판 목록">
			<caption>게시판 목록</caption>
				<form method="post" action="list.inc">
					<div id =search_wrap>
				   		<input type="text" placeholder="검색어를 입력해주세요." 
				   		role="combobox" size="35" name="search">
				   		<input type="submit" id="search_btn" value="검색"/>
				   		<c:if test="${b_list ne null}">
				   			<script>
				   				alert(${search_msg});
				   			</script>
				   		</c:if>
					</div>
				</form>
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="reg">날짜</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="vo" items="${ar }" varStatus="st">
					<tr>
						<td>
							${rowTotal - ((nowPage-1) * blockList + st.index) }
						</td>
						<td style="text-align: left">
						<a href="view.inc?b_idx=${vo.b_idx }&cPage=${nowPage}">
							${vo.subject }
							<c:if test="${vo.c_list ne null }">
									(${vo.c_list.size() })
							</c:if>
						</a>
						</td>
						<td>${vo.writer }</td>
						<td>
							<c:if test="${vo.write_date ne null }">
									${fn:substring(vo.write_date, 0, 16) }
								</c:if>
						</td>
						<td>${vo.hit }</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			    <tr>
			        <td colspan="5" style="list-style: none;">
			              ${pageCode }
						<input type="button" id="write_btn" value="글쓰자!"
						onclick="location.href='write.inc'">
			        </td>
			    </tr>
			    <tr>
			    	<td colspan="5" style="height: 40px;">
			    		
			    	</td>
			    </tr>
			    
			</tfoot>
		</table>
	</div>
	<div>
  		<canvas class="myChart"></canvas>
	</div>
</body>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.6.0/chart.min.js" 
integrity="sha512-GMGzUEevhWh8Tc/njS0bDpwgxdCJLQBWG3Z2Ct+JGOpVnEmjvNx6ts4v6A2XJf1HOrtOsfhv3hBKpK9kE5z8AQ==" 
crossorigin="anonymous" referrerpolicy="no-referrer">
</script>

<script>
	

const myChart = document.getElementById("myChart");
const barChar = new Chart(myChart, {
	  type: "bar", // pie, line, donut, polarArea ...
	  data: {
	    labels: [
	      "봉준호",
	      "타르코프스키",
	      "대런애러노프스키",
	      "드네 빌뇌브",
	      "홍상수",
	    ],
	    datasets: [
	      {
	        label: "영화력",
	      },
	    ],
	  },
	});
</script>
</body>
</html>






