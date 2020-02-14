package com.loccitane.coupon.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.repository.CouponCoreRepository;
import com.loccitane.coupon.repository.CouponMemberRepository;
import com.loccitane.coupon.repository.CouponRepository;
import com.loccitane.user.domain.User;

@Service // 서비스 클래스임을 나타냄
public class CouponService {
	
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponRepository couponRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponMemberRepository couponMemRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponCoreRepository couponCoreRepo;
	
	//모든 쿠폰 리스트 조회
	public List<CouponCore> getCouponList() {
		List<CouponCore> couponData  = couponCoreRepo.findAllByUseyn("Y");
		return couponData;
	}
	
	//해당 고객의 쿠폰 데이터 조회
	public List<Coupon> getUserCoupon(User user) {
		List<Coupon> couponData  = couponRepo.findAllByUserid(user.getUserid());
		return couponData;
	}
	
	//쿠폰 사용처리
	public void useCoupon(String userid, int seq, User loginUser) {
		CouponMember coupon = couponMemRepo.findByCptmseq(seq);
		Date now  = new Date();
		coupon.setCptmusedate(now);
		coupon.setCptmusedyn("Y");
		coupon.setCptmuseuserid(loginUser.getUserid());
		couponMemRepo.save(coupon);
	}
	
	//매장 관리자 고객 쿠폰 부여
	public void giveCoupon(User user, CouponMember coupon, User loginUser) {
		CouponMember newCoupon = new CouponMember();
		Date now  = new Date();
		
		newCoupon.setCptmcpcode(coupon.getCptmcpcode());
		newCoupon.setCptmuserid(user.getUserid());
		newCoupon.setCptmissueday(now);
		newCoupon.setCptmgiveuser(loginUser.getUserid());
		newCoupon.setCptmstartdate(coupon.getCptmstartdate());
		newCoupon.setCptmenddate(coupon.getCptmenddate());
		newCoupon.setCptmusedyn("N");
		
		couponMemRepo.save(newCoupon);
	}
}
