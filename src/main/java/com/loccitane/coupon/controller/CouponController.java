package com.loccitane.coupon.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class CouponController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }

	
	@GetMapping("/manager/couponlist/{userid}") // 고객에 대한 쿠폰조회
	public ModelAndView getUserCoupon(@PathVariable("userid") String userid){
		User userData = service.userCheck(userid); // 서비스에서 요청에 해당하는 처리를 한다.
		ModelAndView nextView = new ModelAndView("jsp/storeManagerCouponList");
		
		List<Coupon> couponList = cpservice.getUserCoupon(userData); // 해당 사용자의 쿠폰리스트 조회
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("couponList", couponList); //쿠폰리스트
		nextView.addObject("searchPhone", userData.getPhone().substring(userData.getPhone().length()-4, userData.getPhone().length())); //사용자데이터
		
		return nextView;
	}
	
	@GetMapping("/manager/couponuse/{userid}/{cptmseq}") // 쿠폰사용처리
	public ModelAndView couponUse(@PathVariable("userid") String userid
			, @PathVariable("cptmseq") int seq, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//세션에서 관리자 정보 가져오기
		HttpSession httpSession = request.getSession(true);
		User loginUser = (User) httpSession.getAttribute("loginUser");
		ModelAndView nextView = null;
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("jsp/logout");
		}else{
			cpservice.useCoupon(userid, seq, loginUser);
			
			nextView = getUserCoupon(userid);
			nextView.addObject("update", "Y"); //업데이트 여부
		}
		return nextView;
	}
	
	//쿠폰 부여
	@PostMapping("/manager/coupongive") 
	public ModelAndView couponGive(User user, CouponMember coupon, HttpServletRequest request, HttpServletResponse response){
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		User loginUser = (User) httpSession.getAttribute("loginUser");
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("jsp/logout");
		}else{
			nextView = new ModelAndView("jsp/storeManagerCouponGive");
			cpservice.giveCoupon(user, coupon, loginUser);
			
			List<CouponCore> couponList = cpservice.getCouponList();
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "Y");
		}
		return nextView;
	}
}
