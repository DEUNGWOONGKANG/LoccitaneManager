package com.loccitane.coupon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.coupon.domain.Coupon;
//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String를 설정>
@Repository
public interface CouponRepository extends JpaRepository<Coupon, String>{
	List<Coupon> findAllByUsercode(String usercode);
	Page<Coupon> findAllByUsedyn(String status, Pageable pageable);
	Page<Coupon> findAllByUsername(String searchKeyword, Pageable pageable);
	Page<Coupon> findAllByUsernameAndUsedyn(String searchKeyword, String status, Pageable pageable);
	Page<Coupon> findAllByCpcode(String searchKeyword, Pageable pageable);
	Page<Coupon> findAllByCpcodeAndUsedyn(String searchKeyword, String status, Pageable pageable);
	Page<Coupon> findAllByCouponno(String searchKeyword, Pageable pageable);
	Page<Coupon> findAllByCouponnoAndUsedyn(String searchKeyword, String status, Pageable pageable);
}
