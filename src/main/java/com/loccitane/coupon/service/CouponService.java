package com.loccitane.coupon.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.repository.CouponMemberRepository;
import com.loccitane.coupon.repository.CouponRepository;
import com.loccitane.user.domain.User;

@Service // 서비스 클래스임을 나타냄
public class CouponService {
	
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponRepository couponRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponMemberRepository couponMemRepo;
	
	// 해당 고객의 쿠폰 데이터 조회
	public List<Coupon> getUserCoupon(User user) {
		List<Coupon> couponData  = couponRepo.findAllByUserid(user.getUserid());
		return couponData;
	}
	
	//쿠폰 사용처리
	public boolean useCoupon(String userid, int seq, User loginUser) {
		CouponMember coupon = couponMemRepo.findBySeq(seq);
		Date now  = new Date();
		coupon.setUsedate(now);
		coupon.setUsedyn("Y");
		coupon.setUseuserid(loginUser.getUserid());
		couponMemRepo.save(coupon);
		
		return true;
	}
}
