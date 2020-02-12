package com.loccitane.coupon.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.repository.CouponRepository;
import com.loccitane.user.domain.User;

@Service // 서비스 클래스임을 나타냄
public class CouponService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponRepository couponRepo;
	
	// 해당 고객의 쿠폰 데이터 조회
	public List<Coupon> getUserCoupon(User user) {
		List<Coupon> couponData  = couponRepo.findAllByUserid(user.getUserid());
		return couponData;
	}
		
}
