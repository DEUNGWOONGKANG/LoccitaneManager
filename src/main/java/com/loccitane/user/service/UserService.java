package com.loccitane.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;

@Service // 서비스 클래스임을 나타냄
public class UserService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	UserRepository userRepo;
	
	// 사용자 전체조회
	public List<User> findAll() {
		List<User> list = userRepo.findAll();
		return list;
	}
	
	// 최초 사용자ID 조회
	public User userCheck(String userid) {
		User userData  = userRepo.findByUserid(userid);
		return userData;
	}
		
	// 사용자(고객) 로그인 
	public User userLogin(User user) {
		User userData = userRepo.findByUseridAndPhoneEndingWith(user.getUserid(), user.getPhone()); 
		return userData;
	}
	
	// 관리자 로그인 
	public User managerLogin(User user) {
		User userData = userRepo.findByUseridAndUserpwAndGrade(user.getUserid(), user.getUserpw(), user.getGrade()); 
		return userData;
	}
	
	// 사용자 리스트 전화번호로 조회
	public List<User> getUserList(User user) {
		List<User> userData = userRepo.findAllByPhoneEndingWith(user.getPhone());
		return userData;
	}
	
	public List<User> searchUserList(String searchKey, String searchKeyword){
		List<User> userData = null;
		if(searchKey.equals("username")) {
			userData = userRepo.findAllByUsername(searchKeyword);
		}else if(searchKey.equals("phone")) {
			userData = userRepo.findAllByPhone(searchKeyword);
		}else{
			userData = userRepo.findAllByUserid(searchKeyword);
		}
		
		return userData;
		
	}
}
