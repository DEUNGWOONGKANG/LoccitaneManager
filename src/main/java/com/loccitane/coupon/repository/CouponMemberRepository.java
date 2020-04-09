package com.loccitane.coupon.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.coupon.domain.CouponMember;
//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String를 설정>
@Repository
public interface CouponMemberRepository extends JpaRepository<CouponMember, String>{
	CouponMember findByCptmseq(int seq);

	List<CouponMember> findAllByCreatedateBetweenOrUsedateBetween(Date yesterday, Date now, Date yesterday2, Date now2);

	CouponMember findByCouponno(String couponNum);

	List<CouponMember> findAllByUseynAndEnddateBetween(String string, Date date1, Date date2);

}
