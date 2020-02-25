package com.loccitane.user.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;
import com.loccitane.utils.ExcelRead;
import com.loccitane.utils.ExcelReadOption;

@Service // 서비스 클래스임을 나타냄
public class UserService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	UserRepository userRepo;
	
	// 사용자 전체조회
	public List<User> findAll() {
		List<User> list = userRepo.findAll();
		return list;
	}
	
	// 사용자 userid값으로 조회
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
	public List<User> getUserListByPhone(User user) {
		ArrayList<String> grade = new ArrayList<String>();
		grade.add("store");
		grade.add("super");
		List<User> userData = userRepo.findAllByGradeNotInAndPhoneEndingWithOrderByStatusAsc(grade,user.getPhone());
		return userData;
	}
	
	// 고객리스트 조회
	public Page<User> getCustomerList(Pageable pageable) {
		ArrayList<String> grade = new ArrayList<String>();
		grade.add("store");
		grade.add("super");
		
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<User> userData = userRepo.findAllByGradeNotIn(grade,pageable);
		return userData;
	}
	
	// 고객리스트 조회
	public Page<User> getCustomerList(Pageable pageable,String searchKey, String searchKeyword) {
		ArrayList<String> grade = new ArrayList<String>();
		grade.add("store");
		grade.add("super");
		
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<User> userData = null;
		
		if(searchKey.equals("username")) {
			userData = userRepo.findAllByUsernameAndGradeNotIn(searchKeyword, grade, pageable);
		}else if(searchKey.equals("phone")) {
			userData = userRepo.findAllByPhoneAndGradeNotIn(searchKeyword, grade, pageable);
		}else if(searchKey.equals("userid")) {
			userData = userRepo.findAllByUseridAndGradeNotIn(searchKeyword, grade, pageable);
		}else{
			userData = userRepo.findAllByGradeNotIn(grade,pageable);
		}
		
		return userData;
	}
	
	//고객 검색
	public List<User> searchUserList(String searchKey, String searchKeyword){
		List<User> userData = null;
		ArrayList<String> grade = new ArrayList<String>();
		grade.add("store");
		grade.add("super");
		
		if(searchKey.equals("username")) {
			userData = userRepo.findAllByUsernameAndGradeNotIn(searchKeyword, grade);
		}else if(searchKey.equals("phone")) {
			userData = userRepo.findAllByPhoneAndGradeNotIn(searchKeyword, grade);
		}else{
			userData = userRepo.findAllByUseridAndGradeNotIn(searchKeyword, grade);
		}
		
		return userData;
		
	}
	
	//회원정보메뉴 등급 + 알람수신여부 저장
	public void saveUser(User user) {
		User userData  = userRepo.findByUserid(user.getUserid());
		Date now  = new Date();
		userData.setGrade(user.getGrade());
		userData.setAlarmyn(user.getAlarmyn());
		userData.setLastupdate(now);
		
		userRepo.save(userData);
	}
	
	public void excelUpload(File destFile) throws Exception{
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A","B","C","D","E","F");
        excelReadOption.setStartRow(2);
        
        try {
	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);
	        
	        for(Map<String, String> article: excelContent){
	            System.out.println(article.get("A"));
	            System.out.println(article.get("B"));
	            System.out.println(article.get("C"));
	            System.out.println(article.get("D"));
	            System.out.println(article.get("E"));
	            System.out.println(article.get("F"));
	            
	        }
        }catch(Exception e) {
        }
	}
}
