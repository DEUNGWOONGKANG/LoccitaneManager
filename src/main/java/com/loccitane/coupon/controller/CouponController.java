package com.loccitane.coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.ApiService;
import com.loccitane.utils.Paging;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class CouponController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	@Autowired
	GradeService grservice;
	@Autowired
	ApiService apiservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }

	//[매장관리자] 고객에 대한 쿠폰조회
	@GetMapping("/store/couponlist/{usercode}/{phone}")
	public ModelAndView getUserCoupon(@PathVariable("usercode") String usercode, @PathVariable("phone") String phone){
		User userData = service.userCheck(usercode); 
		ModelAndView nextView = new ModelAndView("store/storeManagerCouponList");
		
		List<Coupon> couponList = cpservice.getUserCoupon(userData.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("couponList", couponList); //쿠폰리스트
		nextView.addObject("searchPhone", phone); //검색어
		
		return nextView;
	}
	
	//[매장관리자] 쿠폰사용처리
	@GetMapping("/store/couponuse/{usercode}/{cptmseq}/{phone}") 
	public ModelAndView couponUse(@PathVariable("usercode") String usercode
			, @PathVariable("cptmseq") int seq, @PathVariable("phone") String phone, HttpServletRequest request) throws Exception{
		//세션에서 관리자 정보 가져오기
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		ModelAndView nextView = null;
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			//사용처리할 쿠폰 찾기
			CouponMember coupon = cpservice.getCouponMemberInfo(seq);
			Date now  = new Date();
			coupon.setUsedate(now);
			coupon.setUseyn("Y");
			coupon.setUsemanager(loginUser.getId());
			
			//사용처리
			cpservice.useCoupon(coupon);
			//고객페이지 API호출하여 사용처리
			apiservice.coupontomemberModifyCall(coupon);
			
			nextView = getUserCoupon(usercode, phone);
			nextView.addObject("update", "Y"); //업데이트 여부
		}
		return nextView;
	}
	
	//[매장관리자] 쿠폰 부여
	@PostMapping("/store/coupongive") 
	public ModelAndView couponGive(CouponMember coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("store/storeManagerCouponGive");
			//슈퍼관리자의 승인=>즉시발행으로 변경
			cpservice.giveCoupon(coupon, loginUser.getId(), request.getParameter("reason_etc"));
			
			List<CouponCore> couponList = cpservice.getCouponList();
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "Y");
		}
		return nextView;
	}
	
	//[슈퍼관리자]계정관리->회원정보->회원클릭하여 쿠폰발행
	@PostMapping("/super/coupongive") 
	public ModelAndView couponGiveSuper(CouponMember coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("super/superManagerUserInfo");
			cpservice.giveCoupon(coupon, loginUser.getId(), request.getParameter("reason_etc"));
			
			User userData = service.userCheck(coupon.getUsercode());
			List<Grade> gradeList = grservice.getGradeUse();
			List<Coupon> couponList = cpservice.getUserCoupon(coupon.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
			
			nextView.addObject("userData", userData);
			nextView.addObject("gradeList", gradeList);
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "Y");
			
		}
		return nextView;
	}
	
	//[슈퍼관리자]쿠폰관리->쿠폰발행
	@RequestMapping(value= "/super/couponpublish", method = RequestMethod.POST) 
	public ModelAndView couponPublish(CouponMember coupon, HttpServletRequest request) throws Exception { 
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("super/superManagerCouponPublish");
			if(request.getParameter("type").equals("user")) {
				cpservice.giveCoupon(coupon, loginUser.getId(), request.getParameter("reason_etc"));
			} else if(request.getParameter("type").equals("grade")) {
				cpservice.giveCouponToGrade(coupon, loginUser, request);
			}
			List<CouponCore> allCoupon = cpservice.getCouponList(); // 전체쿠폰리스트
			
			nextView.addObject("allCoupon", allCoupon);
			nextView.addObject("saveyn", "Y");
			
		}
		return nextView;
	}
	
	//[슈퍼관리자]계정관리=>회원정보=>쿠폰부여
	@GetMapping("/super/coupongive/{usercode}") 
	public ModelAndView couponGive(@PathVariable("usercode") String usercode){ 
		ModelAndView nextView = new ModelAndView("super/superManagerCouponGive");
		
		User userData = service.userCheck(usercode);
		List<Coupon> couponList = cpservice.getUserCoupon(usercode); // 해당 사용자의 쿠폰리스트 조회
		List<CouponCore> allCoupon = cpservice.getCouponList(); // 전체쿠폰리스트
		
		nextView.addObject("userData", userData);
		nextView.addObject("couponList", couponList);
		nextView.addObject("allCoupon", allCoupon);
		return nextView;
	}
	
	//[슈퍼관리자]쿠폰생성 페이지 이동
	@GetMapping("/super/couponadd")
	public ModelAndView couponAdd() {
		ModelAndView nextView = new ModelAndView("super/superManagerCouponAdd");
		return nextView;
	}
	
	//[슈퍼관리자]신규 쿠폰추가
	@PostMapping("/super/couponsave") 
	public ModelAndView couponSave(CouponCore couponCore, HttpServletRequest request, @PageableDefault Pageable pageable, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession(true);
  		Store loginUser = (Store) httpSession.getAttribute("loginUser");
  		if(loginUser == null) {
  			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('세션이 종료되었습니다.'); history.go(-1);</script>");
            out.flush();
  		}
  		Date now = new Date();
  		couponCore.setCreatedate(now);
  		couponCore.setCreateuser(loginUser.getManagername()+"("+loginUser.getId()+")");
		cpservice.couponSave(couponCore);
		
		//고객페이지내에 있는 쿠폰생성 API 호춣
		apiservice.couponAddCall(couponCore);
		
		ModelAndView nextView = new ModelAndView("super/superManagerCouponList");
		
		Page<CouponCore> couponList = cpservice.getAllCouponList(pageable, null, null);
		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
		nextView.addObject("saveyn", "Y");
		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		
		return nextView;
	}
	
	//[슈퍼관리자]쿠폰 정보 수정
	@PostMapping("/super/couponmodify") 
	public ModelAndView couponModify(CouponCore couponCore, HttpServletRequest request, @PageableDefault Pageable pageable, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession(true);
  		Store loginUser = (Store) httpSession.getAttribute("loginUser");
  		if(loginUser == null) {
  			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('세션이 종료되었습니다.'); history.go(-1);</script>");
            out.flush();
  		}
  		Date now = new Date();
  		couponCore.setCreatedate(now);
  		couponCore.setCreateuser(loginUser.getManagername()+"("+loginUser.getId()+")");
		cpservice.couponSave(couponCore);
		
		//고객페이지내에 있는 쿠폰수정 API 호춣
		apiservice.couponModifyCall(couponCore);
		
		ModelAndView nextView = new ModelAndView("super/superManagerCouponList");
		
		Page<CouponCore> couponList = cpservice.getAllCouponList(pageable, null, null);
		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
		nextView.addObject("saveyn", "Y");
		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		
		return nextView;
	}
	
	//[슈퍼관리자] 쿠폰 상세보기
	@GetMapping("/super/couponinfo/{seq}") 
	public ModelAndView superUserInfo(@PathVariable("seq") int seq){ 
		ModelAndView nextView = new ModelAndView("super/superManagerCouponInfo");
		
		CouponCore couponData = cpservice.getCouponInfo(seq);
		nextView.addObject("couponData", couponData);
	
		return nextView;
	}
	
	//[슈퍼관리자] 발행된 쿠폰삭제
	@PostMapping("/super/coupondelete") 
	public ModelAndView couponDelete(CouponMember cptm, HttpServletRequest request){
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		CouponMember cm = cpservice.getCouponMemberInfo(cptm.getCptmseq());
		//쿠폰삭제 => DB삭제처리가 아닌 useyn을 D 로 바꿔준다.
		cm.setUseyn("D");
		cm.setUsemanager(loginUser.getId());
		Date now = new Date();
		cm.setUsedate(now);
		cpservice.useCoupon(cm);
		//고객페이지 API 호출하여 useyn을 D로 바꿔준다.
		apiservice.coupontomemberModifyCall(cm);
		
		User userData = service.userCheck(cptm.getUsercode());
		List<Grade> gradeList = grservice.getGradeUse();
		List<Coupon> couponList = cpservice.getUserCoupon(cptm.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
		
		ModelAndView nextView = new ModelAndView("super/superManagerUserInfo");
		nextView.addObject("userData", userData);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("couponList", couponList);
		return nextView;
	}
	
	//[슈퍼관리자] 매장발행 엑셀다운로드
	@RequestMapping(value= "/super/exceldown", method = RequestMethod.GET)
	public void superCouponExcelDown(HttpServletResponse response, HttpServletRequest request, @PageableDefault Pageable pageable) throws IOException{ 
		Workbook wb = new XSSFWorkbook();
		//매장에서 발행한 쿠폰 데이터 가져오기
		List<Coupon> couponList = cpservice.getStoreCptm();
		try {
		    // 워크북 생성
		    Sheet sheet = wb.createSheet("COUPON");
		    Row row = null;
		    Cell cell = null;
		    int rowNo = 0;
	
		    // 테이블 헤더용 스타일
		    CellStyle headStyle = wb.createCellStyle();
	
		    // 가는 경계선을 가집니다.
		    headStyle.setBorderTop(BorderStyle.THIN);
		    headStyle.setBorderBottom(BorderStyle.THIN);
		    headStyle.setBorderLeft(BorderStyle.THIN);
		    headStyle.setBorderRight(BorderStyle.THIN);
	
		    // 배경색은 노란색입니다.
		    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
		    // 데이터는 가운데 정렬합니다.
		    headStyle.setAlignment(HorizontalAlignment.CENTER);
		    CreationHelper createHelper = wb.getCreationHelper();
		    // 데이터용 경계 스타일 테두리만 지정
		    CellStyle bodyStyle = wb.createCellStyle();
		    bodyStyle.setBorderTop(BorderStyle.THIN);
		    bodyStyle.setBorderBottom(BorderStyle.THIN);
		    bodyStyle.setBorderLeft(BorderStyle.THIN);
		    bodyStyle.setBorderRight(BorderStyle.THIN);
		    
		    CellStyle dateStyle = wb.createCellStyle();
		    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		    
		    String[] title = {"USERCODE", "USERNAME", "GRADE", "COUPONCODE", "COUPONNO", "COUPONNAME",  "CREATEDATE", "CREATEUSER", "REASON", "STARTDATE", "ENDDATE", "USEYN", "USEMANAGER", "USEDATE"};
		    
		    // 헤더 생성
		    row = sheet.createRow(rowNo++);
		    for(int t=0; t<title.length; t++) {
			    cell = row.createCell(t);
			    cell.setCellStyle(headStyle);
			    cell.setCellValue(title[t]);
		    }
	
		    // 데이터 부분 생성
			for(Coupon coupon : couponList) {
		        row = sheet.createRow(rowNo++);
		        cell = row.createCell(0);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getUsercode());
		        cell = row.createCell(1);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getUsername());
		        cell = row.createCell(2);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getGrade());
		        cell = row.createCell(3);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getCpcode());
		        cell = row.createCell(4);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getCouponno());
		        cell = row.createCell(5);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getCpname());
		        cell = row.createCell(6);
		        cell.setCellStyle(dateStyle);
		        cell.setCellValue(coupon.getCreateday());
		        cell = row.createCell(7);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getCreateuser());
		        cell = row.createCell(8);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getReason());
		        cell = row.createCell(9);
		        cell.setCellStyle(dateStyle);
		        cell.setCellValue(coupon.getStartdate());
		        cell = row.createCell(10);
		        cell.setCellStyle(dateStyle);
		        cell.setCellValue(coupon.getEnddate());
		        cell = row.createCell(11);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getUsedyn());
		        cell = row.createCell(12);
		        cell.setCellStyle(bodyStyle);
		        cell.setCellValue(coupon.getUsemanager());
		        cell = row.createCell(13);
		        cell.setCellStyle(dateStyle);
		        cell.setCellValue(coupon.getUsedate());
		    }
			// 컨텐츠 타입과 파일명 지정
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=couponList.xlsx");

			wb.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			wb.close();
		}
	}
}
