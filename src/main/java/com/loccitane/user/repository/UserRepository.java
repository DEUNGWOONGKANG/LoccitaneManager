package com.loccitane.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.user.domain.User;

//JpaRepository클래스를 상속받는다
//JpaRepository의 파라미터 인자에 <Coupon클래스와 , String을 설정>
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	User findByUserid(String userid);
	User findByUseridAndPhoneEndingWith(String userid, String phone);
	User findByUseridAndUserpwAndGrade(String userid, String userpw, String grade);
	List<User> findAllByPhoneEndingWith(String phone);
}
