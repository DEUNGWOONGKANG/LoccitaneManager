package com.loccitane.user.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	List<User> findAllByGradeNotInAndPhoneEndingWithOrderByStatusAsc(ArrayList<String> grade,String phone);
	List<User> findAllByUsernameAndGradeNotIn(String username, ArrayList<String> grade);
	List<User> findAllByPhoneAndGradeNotIn(String phone, ArrayList<String> grade);
	List<User> findAllByUseridAndGradeNotIn(String userid, ArrayList<String> grade);
	Page<User> findAllByPhoneAndGradeNotIn(String phone, ArrayList<String> grade, Pageable pageable);
	Page<User> findAllByUseridAndGradeNotIn(String userid, ArrayList<String> grade, Pageable pageable);
	Page<User> findAllByUsernameAndGradeNotIn(String username, ArrayList<String> grade, Pageable pageable);
	List<User> findAllByGradeNotInOrderByStatusAsc(ArrayList<String> grade);
	Page<User> findAllByGradeNotIn(ArrayList<String> grade, Pageable pageable);
}
