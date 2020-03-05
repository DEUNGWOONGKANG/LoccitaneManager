package com.loccitane.coupon.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.coupon.domain.CouponTemp;
//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String를 설정>
@Repository
public interface CouponTempRepository extends JpaRepository<CouponTemp, String>{
	Page<CouponTemp> findAllByUsername(String searchKeyword, Pageable pageable);
	Page<CouponTemp> findAllByCpcode(String searchKeyword, Pageable pageable);
	Page<CouponTemp> findAllByCreateuser(String searchKeyword, Pageable pageable);
	CouponTemp findBySeq(int seq);
	List<CouponTemp> findAllByRequestyn(String request);
}
