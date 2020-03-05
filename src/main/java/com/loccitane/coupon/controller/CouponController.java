package com.loccitane.coupon.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.domain.CouponMemberTemp;
import com.loccitane.coupon.domain.CouponTemp;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.Paging;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class CouponController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	@Autowired
	GradeService grservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }

	
	@GetMapping("/manager/couponlist/{usercode}") // 고객에 대한 쿠폰조회
	public ModelAndView getUserCoupon(@PathVariable("usercode") String usercode){
		User userData = service.userCheck(usercode); // 서비스에서 요청에 해당하는 처리를 한다.
		ModelAndView nextView = new ModelAndView("store/storeManagerCouponList");
		
		List<Coupon> couponList = cpservice.getUserCoupon(userData.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("couponList", couponList); //쿠폰리스트
		nextView.addObject("searchPhone", userData.getPhone().substring(userData.getPhone().length()-4, userData.getPhone().length())); //사용자데이터
		
		return nextView;
	}
	
	@GetMapping("/manager/couponuse/{usercode}/{cptmseq}") // 쿠폰사용처리
	public ModelAndView couponUse(@PathVariable("usercode") String usercode
			, @PathVariable("cptmseq") int seq, HttpServletRequest request) throws Exception{
		//세션에서 관리자 정보 가져오기
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		ModelAndView nextView = null;
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			cpservice.useCoupon(usercode, seq, loginUser);
			
			nextView = getUserCoupon(usercode);
			nextView.addObject("update", "Y"); //업데이트 여부
		}
		return nextView;
	}
	
	//쿠폰 부여[매장관리자]
	@PostMapping("/manager/coupongive") 
	public ModelAndView couponGive(CouponMemberTemp coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("store/storeManagerCouponGive");
			cpservice.giveCouponRequest(coupon, loginUser, request);
			
			List<CouponCore> couponList = cpservice.getCouponList();
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "Y");
		}
		return nextView;
	}
	
	//쿠폰 부여[슈퍼관리자] - 계정관리 - 회원정보
	@PostMapping("/super/coupongive") 
	public ModelAndView couponGiveSuper(CouponMember coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("super/superManagerUserInfo");
			cpservice.giveCoupon(coupon, loginUser, request);
			
			User userData = service.userCheck(coupon.getUsercode());
			List<Grade> gradeList = grservice.getGradeUse();
			List<Coupon> couponList = cpservice.getUserCoupon(coupon.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
			
			nextView.addObject("userData", userData);
			nextView.addObject("gradeList", gradeList);
			nextView.addObject("couponList", couponList);
			nextView.addObject("saveyn", "Y");
			
		}
		return nextView;
	}
	
	//쿠폰 부여[슈퍼관리자] - 쿠폰관리 - 쿠폰발행
	@RequestMapping("/super/couponpublish") 
	public ModelAndView couponPublish(CouponMember coupon, HttpServletRequest request) throws Exception { 
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		}else{
			nextView = new ModelAndView("super/superManagerCouponPublish");
			if(request.getParameter("type").equals("user")) {
				cpservice.giveCoupon(coupon, loginUser, request);
			} else if(request.getParameter("type").equals("grade")) {
				cpservice.giveCouponToGrade(coupon, loginUser, request);
			}
			List<CouponCore> allCoupon = cpservice.getCouponList(); // 전체쿠폰리스트
			
			nextView.addObject("allCoupon", allCoupon);
			nextView.addObject("saveyn", "Y");
			
		}
		return nextView;
	}
	
	//계정관리=>회원정보=>쿠폰부여
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
	
	//쿠폰등록페이지 이동
	@GetMapping("/super/couponadd")
	public ModelAndView couponAdd() {
		ModelAndView nextView = new ModelAndView("super/superManagerCouponAdd");
		return nextView;
	}
	
	//관리자  쿠폰추가
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
	
	//쿠폰 정보보기
	@GetMapping("/super/couponinfo/{seq}") 
	public ModelAndView superUserInfo(@PathVariable("seq") int seq){ 
		ModelAndView nextView = new ModelAndView("super/superManagerCouponInfo");
		
		CouponCore couponData = cpservice.getCouponInfo(seq);
		nextView.addObject("couponData", couponData);
	
		return nextView;
	}
	
	//쿠폰 발행 개별승인
	@RequestMapping("/super/approval/{seq}") 
	public ModelAndView couponApproval(@PathVariable("seq") int seq, HttpServletRequest request, Pageable pageable){ 
		cpservice.couponApproval(seq);
		//슈퍼관리자 사용자별 쿠폰 리스트
  		ModelAndView nextView = new ModelAndView("super/superManagerCouponRequestList");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		
  		Page<CouponTemp> couponList = cpservice.couponRequestList(pageable, searchKey, searchKeyword);
  		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
  		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  		nextView.addObject("request", "Y");
		return nextView;
	}
	
	//쿠폰 발행 전체승인
	@RequestMapping("/super/allapproval") 
	public ModelAndView couponAllApproval(HttpServletRequest request, Pageable pageable){ 
		cpservice.allCouponApproval();
		//슈퍼관리자 사용자별 쿠폰 리스트
  		ModelAndView nextView = new ModelAndView("super/superManagerCouponRequestList");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		
  		Page<CouponTemp> couponList = cpservice.couponRequestList(pageable, searchKey, searchKeyword);
  		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
  		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  		nextView.addObject("request", "Y");
		return nextView;
	}
}
