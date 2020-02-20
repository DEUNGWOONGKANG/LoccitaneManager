<%@page import="com.loccitane.user.domain.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<link rel="stylesheet" href="/css/lc_login.css">
<script type="text/javascript">
function check(){
	var id = document.getElementById("userid");
	var pw = document.getElementById("userpw");
	var grade = document.getElementsByName("grade");
	var gradeCheckYn = false;
	var gradeVal = "";
	//������ ������ üũ�Ǿ� �ִ��� Ȯ��
  	for(var i=0; i<grade.length; i++){
		if(grade[i].checked == true){
			gradeCheckYn = true;
			gradeVal = grade[i].value;
		}
	} 
	if(id.value == ""){
		alert("���̵� �Է��ϼ���.");
		id.focus();
		return false;
	}else if(pw.value == ""){
		alert("��й�ȣ�� �Է��ϼ���.");
		pw.focus();
		return false;
	}else if(!gradeCheckYn){
		alert("������ ������ �����ϼ���.");
		return false;
	}else{
		if(gradeVal == "store"){
			document.getElementById("loginForm").submit();
		}else if(gradeVal == "super"){
			document.getElementById("loginForm").action = "/super/login";
			document.getElementById("loginForm").submit();
		}
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
			<input type="radio" name="grade" value="store">
			<label class="custom-control-label" for="jb-radio-1">���������</label>
			<input type="radio" name="grade" value="super">
			<label class="custom-control-label" for="jb-radio-2">���۰�����</label>
		</div>
        <div class="form__field">
          <input type="text" placeholder="ID" id="userid" name="userid">
        </div>

        <div class="form__field">
          <input type="password" placeholder="Password" name="userpw" id="userpw">
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