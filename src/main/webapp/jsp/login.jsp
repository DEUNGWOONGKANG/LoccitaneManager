<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="stylesheet" href="/css/lc_login.css">
<script type="text/javascript">
function check(){
	var id = document.getElementById("loginId");
	var pw = document.getElementById("loginPw");
	var grade = document.getElementsByName("type");
	var gradeCheckYn = false;
	var gradeVal = "";
	//관리자 유형이 체크되어 있는지 확인
  	for(var i=0; i<grade.length; i++){
		if(grade[i].checked == true){
			gradeCheckYn = true;
			gradeVal = grade[i].value;
		}
	} 
	if(id.value == ""){
		alert("아이디를 입력하세요.");
		id.focus();
		return false;
	}else if(pw.value == ""){
		alert("비밀번호를 입력하세요.");
		pw.focus();
		return false;
	}else if(!gradeCheckYn){
		alert("관리자 유형을 선택하세요.");
		return false;
	}else{
		document.getElementById("loginForm").submit();
	}
	
}
</script>
</head>
<body class="align">

  <div class="grid">

    <form id="loginForm" action="/store/login" method="post" class="form login" onsubmit="return check()">
      <header class="login__header">
        <h3 class="login__title"><img src="/img/logo.png" style="width:150px"></h3>
      </header>

      <div class="login__body">
   		<div>
			<input type="radio" name="type" value="store">
			<label class="custom-control-label" for="jb-radio-1">매장관리자</label>
			<input type="radio" name="type" value="super">
			<label class="custom-control-label" for="jb-radio-2">슈퍼관리자</label>
		</div>
        <div class="form__field">
          <input type="text" placeholder="ID" id="loginId" name="loginId">
        </div>

        <div class="form__field">
          <input type="password" placeholder="Password" name="loginPw" id="loginPw">
        </div>

      </div>

      <footer class="login__footer">
        <input type="submit" value="LOGIN">

        <p><span></span><a href="#"></a></p>
      </footer>

    </form>
  </div>
</body>
</html>