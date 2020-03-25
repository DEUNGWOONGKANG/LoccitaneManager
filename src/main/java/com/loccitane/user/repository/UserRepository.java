package com.loccitane.user.repository;

import java.util.Date;
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
	List<User> findAllByPhoneEndingWithOrderByStatusAsc(String phone);
	Page<User> findAllByPhoneContaining(String phone, Pageable pageable);
	Page<User> findAllByUsernameContaining(String username, Pageable pageable);
	Page<User> findAllByUsercodeContaining(String code, Pageable pageable);
	List<User> findAllByUsername(String searchKeyword);
	List<User> findAllByPhone(String searchKeyword);
	List<User> findAllByUsercode(String searchKeyword);
	List<User> findAllByGrade(String grade);
	User findByUsercode(String usercode);
	User findByUsercodeAndPhoneEndingWith(String usercode, String phone);
	List<User> findAllByLastupdateBetween(Date yesterday, Date now);
	List<User> findAllByGradeAndTotalbuyGreaterThanEqualOrGradeAndTotalbuyGreaterThanEqualOrGradeAndTotalbuyGreaterThanEqual(
			String string, int i, String string2, int j, String string3, int k);
	List<User> findAllByGradeInAndBirthdayIsNotNull(List<String> grades);
	List<User> findAllByStatus(String string);
	List<User> findAllByGradeAndStatus(String grade, String string);
}
