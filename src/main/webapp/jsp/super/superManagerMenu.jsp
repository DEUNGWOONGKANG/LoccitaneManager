<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<%
	String menuName = (String)session.getAttribute("menu");
%>
</head>
<body>
<div id="sidebar" class="sidebar py-3">
  <div class="text-gray-400 text-uppercase px-3 px-lg-4 py-4 font-weight-bold small headings-font-family">ADMIN PAGE</div>
  <ul class="sidebar-menu list-unstyled">
    <!-- <li class="sidebar-list-item" id="menu1"><a id="menu1a" href="/super/menu/menu1" class="sidebar-link text-muted"><i class="o-home-1 mr-3 text-gray"></i><span><b>Home</b></span></a></li>-->
    <li class="sidebar-list-item" id="menu2"><a id="menu2a" href="#" data-toggle="collapse" data-target="#pages" aria-expanded="false" aria-controls="pages" class="sidebar-link text-muted"><i class="o-database-1 mr-3 text-gray"></i><span><b>계정관리</b></span></a>
      <div id="pages" class="collapse">
        <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
          <li class="sidebar-list-item" id="menu2_1"><a id="menu2_1a" href="/super/menu/menu2_1" class="sidebar-link text-muted pl-lg-5"><b>회원정보</b></a></li>
          <li class="sidebar-list-item" id="menu2_2"><a id="menu2_2a" href="/super/menu/menu2_2" class="sidebar-link text-muted pl-lg-5"><b>엑셀업로드</b></a></li>
        </ul> 
      </div>
    </li>
        <li class="sidebar-list-item" id="menu3"><a id="menu3a" href="/super/menu/menu3" class="sidebar-link text-muted"><i class="o-imac-screen-1 mr-3 text-gray"></i><span><b>매장관리</b></span></a></li>
    	<li class="sidebar-list-item" id="menu4"><a id="menu4a" href="#" data-toggle="collapse" data-target="#pages2" aria-expanded="false" aria-controls="pages2" class="sidebar-link text-muted"><i class="o-wireframe-1 mr-3 text-gray"></i><span><b>쿠폰관리</b></span></a>
	     <div id="pages2" class="collapse">
	       <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
	         <li class="sidebar-list-item" id="menu4_1"><a id="menu4_1a" href="/super/menu/menu4_1" class="sidebar-link text-muted pl-lg-5"><b>쿠폰관리</b></a></li>
	         <li class="sidebar-list-item" id="menu4_2"><a id="menu4_2a" href="/super/menu/menu4_2" class="sidebar-link text-muted pl-lg-5"><b>쿠폰발행</b></a></li>
	         <li class="sidebar-list-item" id="menu4_3"><a id="menu4_3a" href="/super/menu/menu4_3" class="sidebar-link text-muted pl-lg-5"><b>사용자별쿠폰관리</b></a></li>
	         <!-- <li class="sidebar-list-item" id="menu4_4"><a id="menu4_4a" href="/super/menu/menu4_4" class="sidebar-link text-muted pl-lg-5"><b>쿠폰요청승인</b></a></li>-->
	       </ul>
	     </div>
	   </li>
	    <li class="sidebar-list-item" id="menu5"><a id="menu5a" href="#" data-toggle="collapse" data-target="#pages3" aria-expanded="false" aria-controls="pages3" class="sidebar-link text-muted"><i class="o-sales-up-1 mr-3 text-gray"></i><span><b>통계</b></span></a>
	     <div id="pages3" class="collapse">
	       <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
	         <li class="sidebar-list-item" id="menu5_1"><a id="menu5_1a" href="/super/menu/menu5_1" class="sidebar-link text-muted pl-lg-5"><b>시간별 사용량</b></a></li>
	         <li class="sidebar-list-item" id="menu5_2"><a id="menu5_2a" href="/super/menu/menu5_2" class="sidebar-link text-muted pl-lg-5"><b>일자별 사용량</b></a></li>
	         <li class="sidebar-list-item" id="menu5_3"><a id="menu5_3a" href="/super/menu/menu5_3" class="sidebar-link text-muted pl-lg-5"><b>쿠폰별 사용량</b></a></li>
	       </ul>
	     </div>
	   </li>
        <li class="sidebar-list-item" id="menu6"><a id="menu6a" href="/super/menu/menu6" class="sidebar-link text-muted"><i class="o-table-content-1 mr-3 text-gray"></i><span><b>등급관리</b></span></a></li>
        <li class="sidebar-list-item" id="menu7"><a id="menu7a" href="/super/menu/menu7" class="sidebar-link text-muted"><i class="o-survey-1 mr-3 text-gray"></i><span><b>카카오톡 발송관리</b></span></a></li>
  </ul>
</div>
</body>
<script type="text/javascript">
var menu =  "<%=menuName%>";
active(menu);

function active(num){
	var menu = document.getElementById("sidebar").getElementsByTagName("li");
	 for(var i=0; i<menu.length; i++){
		if(num == menu[i].id){
			document.getElementById(menu[i].id+"a").className = document.getElementById(menu[i].id+"a").className + " active";
			
			if(num == "menu2_1" || num == "menu2_2"){
				document.getElementById("pages").className = document.getElementById("pages").className + " show";
			}else if(num == "menu4_1" || num == "menu4_2" || num == "menu4_3" || num == "menu4_4"){
				document.getElementById("pages2").className = document.getElementById("pages2").className + " show";
			}else if(num == "menu5_1" || num == "menu5_2" || num == "menu5_3"){
				document.getElementById("pages3").className = document.getElementById("pages3").className + " show";
			}
		}
	} 
}

</script>
</html>