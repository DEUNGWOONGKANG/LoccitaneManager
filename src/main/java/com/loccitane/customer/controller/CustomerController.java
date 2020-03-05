package com.loccitane.customer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;

@RestController
public class CustomerController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	
	//최초 고객ID 체크 후 휴대폰번호 4자리 입력 후 로그인 체크
	@ResponseBody
  	@RequestMapping("/customer/login") 
  	public List<Coupon> login(@RequestParam String code, @RequestParam String phone){ 
  		User newUser = new User();
  		newUser.setUsercode(code);
  		newUser.setPhone(phone);
  		
  		User userData = service.userLogin(newUser); //해당 유저id값+전화번호 뒷자리4자리로 사용자 존재여부 확인
  		List<Coupon> couponList = cpservice.getUserCoupon(userData.getUsercode()); // 해당 사용자의 쿠폰리스트 조회
  		return couponList;
  	}

}
