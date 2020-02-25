<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div id="sidebar" class="sidebar py-3">
  <div class="text-gray-400 text-uppercase px-3 px-lg-4 py-4 font-weight-bold small headings-font-family">ADMIN PAGE</div>
  <ul class="sidebar-menu list-unstyled">
    <li class="sidebar-list-item"><a href="/super/home" class="sidebar-link text-muted"><i class="o-home-1 mr-3 text-gray"></i><span><b>Home</b></span></a></li>
    <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages" aria-expanded="false" aria-controls="pages" class="sidebar-link text-muted"><i class="o-database-1 mr-3 text-gray"></i><span><b>계정관리</b></span></a>
      <div id="pages" class="collapse show">
        <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
          <li class="sidebar-list-item"><a href="/super/userlist" class="sidebar-link text-muted pl-lg-5 active"><b>회원정보</b></a></li>
          <li class="sidebar-list-item"><a href="/super/excelupload" class="sidebar-link text-muted pl-lg-5"><b>엑셀업로드</b></a></li>
        </ul>
      </div>
    </li>
        <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-imac-screen-1 mr-3 text-gray"></i><span><b>매장관리</b></span></a></li>
    <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages2" aria-expanded="false" aria-controls="pages2" class="sidebar-link text-muted"><i class="o-wireframe-1 mr-3 text-gray"></i><span><b>쿠폰관리</b></span></a>
     <div id="pages2" class="collapse">
       <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
         <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>쿠폰관리</b></a></li>
         <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>사용자별쿠폰관리</b></a></li>
       </ul>
     </div>
   </li>
    <li class="sidebar-list-item"><a href="#" data-toggle="collapse" data-target="#pages3" aria-expanded="false" aria-controls="pages3" class="sidebar-link text-muted"><i class="o-sales-up-1 mr-3 text-gray"></i><span><b>통계</b></span></a>
     <div id="pages3" class="collapse">
       <ul class="sidebar-menu list-unstyled border-left border-primary border-thick">
         <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>시간별 사용량</b></a></li>
         <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>일자별 사용량</b></a></li>
         <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted pl-lg-5"><b>쿠폰별 사용량</b></a></li>
       </ul>
     </div>
   </li>
        <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-table-content-1 mr-3 text-gray"></i><span><b>등급관리</b></span></a></li>
        <li class="sidebar-list-item"><a href="#" class="sidebar-link text-muted"><i class="o-survey-1 mr-3 text-gray"></i><span><b>카카오톡 발송관리</b></span></a></li>
  </ul>
</div>
</body>
</html>