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
    <!-- Favicon-->
    <!-- <link rel="shortcut icon" href="img/favicon.png?3">-->
<script type="text/javascript">
var progress;

function check() {
    var file = $("#excelFile").val();
    if (file == "" || file == null) {
        alert("파일을 선택해주세요.");
        return false;
    } else if (!checkFileType(file)) {
        alert("엑셀 파일만 업로드 가능합니다.");
        return false;
    }

    var progressbar = document.getElementById('progress');
    progressbar.style.display= 'block';
    progressbar.style.width ='0px';

    if (confirm("업로드 하시겠습니까?")) {
    	$("#excelUploadForm").ajaxForm({
            beforeSend: function(){
                $("#excelUploadingState").html("<font size = 2><b>0% </b></font>");
                
                progress = setInterval(excelUploadProgress, 50);
                
            },
            success: function() {
                document.getElementById('progress').style.width = '100%';
                $("#excelUploadingState").html("<font size = 2><b>100% </b></font>");
                
                clearInterval(progress);                    
                
                //location.reload();
                excelUploadProgressClear();
            },
            error: function(request,status,error){
            	//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

                alert("에러가 발생하였습니다.");
                clearInterval(progress);    
                //location.reload();
                //에러 발생시 소스를 넣는다.
                excelUploadProgressClear();
            }
        }); 
            $("#excelUploadForm").submit();

    }
}
function excelUploadProgress(){
    //count를 0으로 초기화 해야하는가?
    $.ajax({
        type: "post",
        url: "/excelUploadProgress",
        data: {},
        dataType :"text",
        success : function(resultData){
//             console.log(resultData);
            if(resultData == 100){
                clearInterval(progress);    
            }
            $("#excelUploadingState").html("<font size = 2><b>"+ resultData +"% </b></font>");
            document.getElementById('progress').style.width = (resultData*4.32).toString() +'px';
        },
        error: function(e){
            
        }
    });                        
}
function excelUploadProgressClear(){
    
    $.ajax({
        type: "post",
        url: "/excelUploadProgressClear",
        data: {},
        dataType :"text",
        success : function(resultData){
        },
        error: function(e){
            
        }
    });        
}
function checkFileType(filePath) {
    var fileFormat = filePath.split(".");
    if (fileFormat.indexOf("xlsx") > -1) {
        return true;
    } else {
        return false;
    }
}
</script>
  </head>
  <body>
    <!-- navbar-->
    <header class="header">
      <nav class="navbar navbar-expand-lg px-4 py-2 bg-white shadow"><a href="#" class="sidebar-toggler text-gray-500 mr-4 mr-lg-5 lead"><i class="fas fa-align-left"></i></a><a href="/super/home" class="navbar-brand font-weight-bold text-uppercase text-base"><img src="<%=url %>/img/logo.png" style="width:150px"></a>
        <ul class="ml-auto d-flex align-items-center list-unstyled mb-0">
          <li class="nav-item dropdown ml-auto"><a id="userInfo" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle"><span style="font-size:12px;"><%=user.getUsername() %>님</span></a>
            <div aria-labelledby="userInfo" class="dropdown-menu">
              <a href="/manager/logout" class="dropdown-item">Logout</a>
            </div>
          </li>
        </ul>
      </nav>
    </header>
    <div class="d-flex align-items-stretch">
      <jsp:include page="superManagerMenu.jsp"></jsp:include>
      <div class="w-100 d-flex flex-wrap">
      	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action= "/super/excelUploadAjax" style="margin-left:auto; margin-right:auto; margin-top:50px;">
      		<div class="input-group">
			  <div class="custom-file">
			    <input type="file" class="custom-file-input" id="excelFile" name="excelFile">
			    <label class="custom-file-label" for="excelFile" id="fileName">파일을 선택해주세요</label>
			  </div>
			  <div class="input-group-append">
			    <button class="btn btn-outline-secondary" type="button" onclick="check()">엑셀업로드</button>
			  </div>
			</div>
			<div class="progress" id="progress_info">
			  <div class="progress-bar" role="progressbar" aria-valuenow="70"
			  aria-valuemin="0" aria-valuemax="100" id="progress">
			    <p id= "excelUploadingState" class="excelUploadingState"></p>
			  </div>
		    </div>
		</form> 
		<table class="couponInfo">
			<thead>
			<tr>
				<th>NO</th>
				<th>업로드일시</th>
				<th>작업자</th>
				<th>작업내용</th>
			</tr>
			</thead><!-- #userList Header -->
			<tbody>
			<c:if test="${!empty logList}">
			<c:forEach var="log" items="${logList}">
				<tr>
					<td>${log.seq }</td>
					<td><fmt:formatDate value="${log.logdate}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
					<td>${log.username}[${log.userid}]</td>
					<td>${log.logcontent}</td>
				</tr><!-- #userList Row -->
			</c:forEach>
			</c:if>
			</tbody>
		</table>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="<%=url %>/js/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="<%=url %>/js/bootstrap.js"></script>
    <script src="<%=url %>/js/jquery.cookie.js"> </script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="<%=url %>/js/front.js"></script>
    <script src="<%=url %>/js/jquery.form.min.js"></script>
    <script type="text/javascript">
   	$(".custom-file-input").on('change', function(){
   		var fileName = $(this).val().split("\\").pop();
   	  	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
   	});
    </script>
  </body>
</html>