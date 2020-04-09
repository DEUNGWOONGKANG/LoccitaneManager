<%@include file="common.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%    request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<title>Loccitane Admin Page</title>
<!-- Bootstrap CSS-->
<link rel="stylesheet" href="<%=url %>/css/bootstrap.css">
<!-- Font Awesome CSS-->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" >
<!-- Google fonts - Popppins for copy-->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,800">
<!-- orion icons-->
<link rel="stylesheet" href="<%=url %>/css/orionicons.css">
<!-- theme stylesheet-->
<link rel="stylesheet" href="<%=url %>/css/style.default.css" id="theme-stylesheet">
<link rel="stylesheet" href="<%=url %>/css/jquery-ui.css">
<script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
<script src="<%=url %>/js/jquery-ui.js"></script>
<!-- Favicon-->
<!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
if("${sendyn}" == "Y"){
	alert("발송 대기 리스트에 추가되었습니다.");	
}
function check(){
	var template = document.getElementById("template");
	var type = document.getElementsByName("type");
	var typeCheckYn = false;
	var typeVal = "";
	var username = document.getElementById("username");
	
	for(var i=0; i<type.length; i++){
		if(type[i].checked == true){
			typeCheckYn = true;
			typeVal = type[i].value;
		}
	} 
	if(template.options[template.selectedIndex].value == "00000"){
		alert("템플릿을 선택해주세요.");
		return false;		
	}
	if(!typeCheckYn){
		alert("발송대상타입을 선택해주세요.");
		return false;
	}else{
		if(typeVal == "user"){
			if(username.value == "" || username.value == null){
				alert("발송대상고객을 선택해주세요");
				return false;
			}
		}
	}
	
	var result = confirm(username.value+" 고객님에게 카카오톡을 발송하시겠습니까?");
	
	if(result){
		document.getElementById("formdata").submit();
	}else{
		return false;
	}
}
function userSearch(){
	var searchKey = document.getElementById("searchKey");
	var searchKeyword = document.getElementById("searchKeyword").value;
	searchKeyword = searchKeyword.replace(/ /g,"");
	if(searchKeyword == "" || searchKeyword == null){
		alert("검색어를 입력해주세요.");
		return false;
	}
	
	window.open('/store/userSearch/'+searchKey.options[searchKey.selectedIndex].value+'/'+searchKeyword
			, 'userSearchPop', 'height='+ screen.height + 'width=' + screen.width + 'fullscreen=yes');
}
function usertype(type){
	if(type == "user"){
		$('#gradetr').css('display', 'none');
		$('#usertr1').css('display', '');
		$('#usertr2').css('display', '');
	}else if(type == "grade"){
		$('#gradetr').css('display', '');
		$('#usertr1').css('display', 'none');
		$('#usertr2').css('display', 'none');
	}
}

function formchange(){
	var select = document.getElementById("template");
	var val = select.options[select.selectedIndex].value;

	if(val == "00000"){
		$('#content').css('display', 'none');
	}else if(val == "10027"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10027.png";
	}else if(val == "10028"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10028.png";
	}else if(val == "10030"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10030.png";
	}else if(val == "10031"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10031.png";
	}else if(val == "10049"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10049.png";
	}else if(val == "10050"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10050.png";
	}else if(val == "10051"){
		$('#content').css('display', '');
		view.src = "<%=url %>/img/10051.png";
	}
}
</script>
</head>
  <body>
    <!-- navbar-->
    <jsp:include page="superManagerHeader.jsp"></jsp:include>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<form id="formdata" action="/super/kakaosend" method="post" style="width:100%" onsubmit="return check()">
      	<input type="hidden" id="adminId" name="adminId" value="<%=user.getId() %>">
      	<input type="hidden" id="adminName" name="adminName" value="<%=user.getName() %>">
      	<div style="width:100%">
	      	<table class="couponInfo">
			<tbody>
				<tr height="70px">
					<th width="25%">알림톡 템플릿</th>
					<td>
						<select class="form-control" id="template" name="template" onchange="formchange()">
		   					<option value="00000">템플릿 선택</option>
		   					<option value="10027">[10027]현재 등급안내</option>
		   					<option value="10028">[10028]등급업 축하 쿠폰 발급안내</option>
		   					<option value="10030">[10030]등급 첫 구매 쿠폰 발급안내(REGULAR,PREMIUM)</option>
		   					<option value="10031">[10031]등급 첫 구매 쿠폰 발급안내(LOYAL,PRESTIGE)</option>
		   					<option value="10049">[10049]쿠폰 소멸 예정 안내</option>
		   					<option value="10050">[10050]프레스티지 스페셜 쿠폰 발급안내</option>
		   					<option value="10051">[10051]프레스티지 등급 신제품 선공개 쿠폰 발급안내</option>
				    	</select>
				    	<br>사전에 등록된 템플릿만 발송이 가능합니다.
					</td>
				</tr>
				<tr id="content" style="display:none;">
					<th width="25%">발송내용</th>
					<td>
						<img id="view" src="<%=url %>/img/logo.png" style="width:400px">
					</td>
				</tr>
				<tr>
					<th width="25%">발송대상타입</th>
					<td>
						<input type="radio" name="type" value="user" onclick="usertype('user')"> 사용자별 발송&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="type" value="grade" onclick="usertype('grade')"> 등급별 발송
					</td>
				</tr>
				<tr height="70px" id="usertr1" style="display:none;">
					<th width="25%" rowspan=2>발송대상고객(사용자별)</th>
					<td>
						<select class="form-control" id="searchKey">
							<option value="username">이름</option>
							<option value="phone">전화번호</option>
							<option value="usercode">회원번호</option>
						</select>
						<input type="text" id="searchKeyword" class="form-control">  
						<input id="searchbtn" type="button" class="btn btn-warning" value="검색" onclick="userSearch()">
					</td>
				</tr>
				<tr height="70px" id="usertr2" style="display:none;">
					<td style="border-left:1px solid #e0e0e0;">
						<input type="hidden" id="usercode" name="usercode">
						이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름: <input type="text" class="form-control" id="username" name="username" readonly >
						연락처: <input type="text" class="form-control" id="phone" name="phone" readonly ><br>
					</td>
				</tr>
				<tr height="70px" id="gradetr" style="display:none;">
					<th width="25%">발송대상고객(등급별)</th>
					<td>
						<select id="grade" name="grade" class="form-control">
							<option value="ALL">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;모든등급 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </option>
							<c:forEach var="grade" items="${gradeList}">
								<option value="${grade.name}" >${grade.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
			</tbody>
			</table>
			<div class="btndiv">
				<button type="submit" class="btn btn-warning">발송</button>
			</div>
		</div>
		</form>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
  </body>
</html>