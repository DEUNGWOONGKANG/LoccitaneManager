package com.loccitane.coupon.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;

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
		ModelAndView nextView = new ModelAndView("jsp/storeManagerCouponList");
		
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
			nextView = new ModelAndView("jsp/logout");
		}else{
			cpservice.useCoupon(usercode, seq, loginUser);
			
			nextView = getUserCoupon(usercode);
			nextView.addObject("update", "Y"); //업데이트 여부
		}
		return nextView;
	}
	
	//쿠폰 부여[매장관리자]
	@RequestMapping("/manager/coupongive") 
	public ModelAndView couponGive(User user, CouponMember coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("jsp/logout");
		}else{
			nextView = new ModelAndView("jsp/storeManagerCouponGive");
			String reason = request.getParameter("reason");
			String reason_etc = request.getParameter("reason_etc");
			if(reason.equals("1")) {
				coupon.setReason("교환/환불");
			}else if(reason.equals("2")){
				coupon.setReason("사용기한 만료");
			}else if(reason.equals("3")){
				coupon.setReason(reason_etc);
			}
			
			cpservice.giveCoupon(user, coupon, loginUser);
			
			List<CouponCore> couponList = cpservice.getCouponList();
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "Y");
		}
		return nextView;
	}
	
	//쿠폰 부여[슈퍼관리자]
	@PostMapping("/super/coupongive") 
	public ModelAndView couponGiveSuper(User user, CouponMember coupon, HttpServletRequest request){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("jsp/logout");
		}else{
			nextView = new ModelAndView("jsp/superManagerUserInfo");
			cpservice.giveCoupon(user, coupon, loginUser);
			
			User userData = service.userCheck(user.getUsercode());
			List<Grade> gradeList = grservice.findAll();
			List<Coupon> couponList = cpservice.getUserCoupon(user.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
			
			nextView.addObject("userData", userData);
			nextView.addObject("gradeList", gradeList);
			nextView.addObject("couponList", couponList);
			nextView.addObject("saveyn", "Y");
			
		}
		return nextView;
	}
	
	//계정관리=>회원정보=>쿠폰부여
	@GetMapping("/super/coupongive/{usercode}") 
	public ModelAndView couponGive(@PathVariable("usercode") String usercode){ 
		ModelAndView nextView = new ModelAndView("jsp/superManagerCouponGive");
		
		User userData = service.userCheck(usercode);
		List<Coupon> couponList = cpservice.getUserCoupon(usercode); // 해당 사용자의 쿠폰리스트 조회
		
		nextView.addObject("userData", userData);
		nextView.addObject("couponList", couponList);
		return nextView;
	}
}
