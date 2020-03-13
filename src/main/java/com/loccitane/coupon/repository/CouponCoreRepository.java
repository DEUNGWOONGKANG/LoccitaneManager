package com.loccitane.coupon.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.coupon.domain.CouponCore;
//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String를 설정>
@Repository
public interface CouponCoreRepository extends JpaRepository<CouponCore, Integer>{
	List<CouponCore> findAllByUseyn(String useyn);
	Page<CouponCore> findAllByCpcodeContaining(String searchKeyword, Pageable pageable);
	Page<CouponCore> findAllByCpnameContaining(String searchKeyword, Pageable pageable);
	Page<CouponCore> findAllByCreateuserContaining(String searchKeyword, Pageable pageable);
	Page<CouponCore> findAllByUseyn(String searchKeyword, Pageable pageable);
	CouponCore findBySeq(int seq);
	List<CouponCore> findAllByCreatedateBetween(Date yesterday, Date now);
}
