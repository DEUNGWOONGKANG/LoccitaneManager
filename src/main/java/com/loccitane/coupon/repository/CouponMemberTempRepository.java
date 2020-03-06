package com.loccitane.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.coupon.domain.CouponMemberTemp;
//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String를 설정>
@Repository
public interface CouponMemberTempRepository extends JpaRepository<CouponMemberTemp, String>{
	CouponMemberTemp findBySeq(int seq);
}