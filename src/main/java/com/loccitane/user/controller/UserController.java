package com.loccitane.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class UserController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	
	//최초 고객ID 체크 후 휴대폰번호 4자리 입력 후 로그인 체크
	@PostMapping("/user/login") 
	public ModelAndView loginUser(User user, HttpServletResponse response) throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/customerCouponList");
		User userData = service.userLogin(user); //해당 유저id값+전화번호 뒷자리4자리로 사용자 존재여부 확인
		if(userData == null) { //사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 고객 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}else { // 사용자가 존재하는 경우
			List<Coupon> couponList = cpservice.getUserCoupon(user); // 해당 사용자의 쿠폰리스트 조회
			nextView.addObject("userData", userData); //사용자데이터
			nextView.addObject("couponList", couponList); //쿠폰리스트
			nextView.addObject("tel", user.getPhone()); //핸드폰번호 뒤4자리
		}
		return nextView;
	}
	
	@SuppressWarnings("null")
	@GetMapping("/user/check/{lc_user_id}") // 최초 고객 URL 랜딩페이지에서 ID값 체크
	public ModelAndView checkUser(@PathVariable("lc_user_id") String userid){
		User user = service.userCheck(userid); // 서비스에서 요청에 해당하는 처리를 한다.
		ModelAndView nextView = new ModelAndView("jsp/customerMain");
		if(user == null) {
			//존재하지 않는 ID일경우 휴대폰 번호 입력란 DISABLED 처리
			nextView.addObject("check", "N");
		}else{
			//존재하는 ID일 경우 정상적으로 페이지 접근
			nextView.addObject("check", "Y");
			nextView.addObject("userid", userid);
		}
		return nextView;
	}
	
	//로그인 시도
	@PostMapping("/manager/login") 
	public ModelAndView loginManager(User user, HttpServletResponse response) throws Exception { 
		ModelAndView nextView = null;
		User userData = service.managerLogin(user);// 매니저 로그인 DB체크
		
		if(userData == null) { //사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 사용자 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}
		if(user.getGrade().equals("store")) { //매장관리자일 경우
			nextView = new ModelAndView("jsp/storeManagerCouponUse");
		}else if(user.getGrade().equals("super")) { // 슈퍼관리자일 경우
			nextView = new ModelAndView("jsp/superManagerMain");
		}
		nextView.addObject("userData", userData); //사용자데이터
		
		return nextView;
	}
	
	//최초 로그인 페이지
	@GetMapping("/") 
	public ModelAndView main() throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/login");
		return nextView;
	}
	
	//매장 매니저 - 사용자 뒷번호로 검색
	@PostMapping("/manager/userSearch") 
	public ModelAndView getUserList(User user, HttpServletResponse response) throws Exception {
		ModelAndView nextView = new ModelAndView("jsp/storeManagerUserList");
		List<User> userData = service.getUserList(user);
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("searchPhone", user.getPhone()); //사용자데이터
		return nextView;
	}
	
	@GetMapping("/manager/couponlist/{lc_user_id}") // 고객에 대한 쿠폰조회
	public ModelAndView getUserCoupon(@PathVariable("lc_user_id") String userid){
		User userData = service.userCheck(userid); // 서비스에서 요청에 해당하는 처리를 한다.
		ModelAndView nextView = new ModelAndView("jsp/storeManagerCouponList");
		
		List<Coupon> couponList = cpservice.getUserCoupon(userData); // 해당 사용자의 쿠폰리스트 조회
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("couponList", couponList); //쿠폰리스트
		nextView.addObject("searchPhone", userData.getPhone().substring(userData.getPhone().length()-4, userData.getPhone().length())); //사용자데이터
		
		return nextView;
	}
}
