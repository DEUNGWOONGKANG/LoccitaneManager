package com.loccitane.user.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.log.domain.Log;
import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;
import com.loccitane.utils.ExcelRead;
import com.loccitane.utils.ExcelReadOption;

@Service // 서비스 클래스임을 나타냄
public class UserService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	UserRepository userRepo;
	
	private static int totalRowCount = 0; // 전체 행 개수
    private static int updateRowCount = 0; // 성공한 데이터 개수
    private static int noupdateRowCount = 0; // 변경사항없는 데이터 개수
    private static int insertRowCount = 0; // 신규추가 데이터 개수
    
    private static int currentStateCount = 0; 
    //변수들을 다 더한 값이다. 
    //for문이 돌면서 현재 몇개가 처리되었는지 알게 해주는 변수
    private static String currentState; //현재 상태 
 
    
    public static int getInsertRowCount() {
		return insertRowCount;
	}

	public static void setInsertRowCount(int insertRowCount) {
		UserService.insertRowCount = insertRowCount;
	}

	public static int getTotalRowCount() {
        return totalRowCount;
    }
 
    public static void setTotalRowCount(int totalRowCount) {
        UserService.totalRowCount = totalRowCount;
    }

    public static int getUpdateRowCount() {
		return updateRowCount;
	}

	public static void setUpdateRowCount(int updateRowCount) {
		UserService.updateRowCount = updateRowCount;
	}

	public static int getNoupdateRowCount() {
		return noupdateRowCount;
	}

	public static void setNoupdateRowCount(int noupdateRowCount) {
		UserService.noupdateRowCount = noupdateRowCount;
	}

	public static String getCurrentState() {
        return currentState;
    }
 
    public static void setCurrentState(String currentState) {
    	UserService.currentState = currentState;
    }
 
    public static int getCurrentStateCount() {
        return currentStateCount;
    }
 
    public static void setCurrentStateCount(int currentStateCount) {
    	UserService.currentStateCount = currentStateCount;
    }

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
	
	public String excelUpload(File destFile) throws Exception{
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		ExcelReadOption excelReadOption = new ExcelReadOption();
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L");
		excelReadOption.setStartRow(2);
		Date now = new Date();
		
		setCurrentState("A"); // 엑셀파일 읽어 오기 전 상태를 A
      
		List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);
		setTotalRowCount(excelContent.size());
		
		if(excelContent.size() > 0) {
			setCurrentState("B");
		    List<User> saveList = new ArrayList<User>();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(now);
		    cal.add(Calendar.YEAR,  -1);
		    
		    for(Map<String, String> article: excelContent){
		    	User check = userCheck(article.get("B"));
		    	boolean dataAdd = false;
		    	if(check == null) {
		    		//DB에 사용자가 없는 경우 신규 생성
		    		check = new User();
		    		check.setVipcode(article.get("A"));
		    		check.setUserid(article.get("B"));
		    		check.setUserpw(article.get("C"));
		    		check.setUsername(article.get("D"));
		    		check.setBirthday(article.get("E"));
		    		check.setStore(article.get("F"));
		    		check.setPosition(article.get("G"));
		    		check.setPhone(article.get("H"));
		    		check.setLastpurchase(transFormat.parse(article.get("I")));
		    		check.setTotalbuy(Integer.parseInt(article.get("J")));
		    		check.setAlarmyn(article.get("K"));
		    		if(check.getLastpurchase().before(cal.getTime())) {
		    			check.setStatus("9");
		    		}else {
		    			check.setStatus("1");
		    		}
		    		dataAdd = true;
		    		insertRowCount++;
		    	}else{
		    		// DB에 사용자가 있는 경우 각 컬럼 체크하여 변경사항만 적용
		    		// 변경된 사항이 없을경우 UPDATE 하지 않음.
		    		if(!check.getVipcode().equals(article.get("A"))) {
		    			check.setVipcode(article.get("A"));
		    			dataAdd = true;
		    		}
		    		if(!check.getUserid().equals(article.get("B"))) {
		    			check.setUserid(article.get("B"));
		    			dataAdd = true;
		    		}
		    		if(!check.getUserpw().equals(article.get("C"))) {
		    			check.setUserpw(article.get("C"));
		    			dataAdd = true;
		    		}
		    		if(!check.getUsername().equals(article.get("D"))) {
		    			check.setUsername(article.get("D"));
		    			dataAdd = true;
		    		}
		    		if(!check.getBirthday().equals(article.get("E"))) {
		    			check.setBirthday(article.get("E"));
		    			dataAdd = true;
		    		}
		    		if(!check.getStore().equals(article.get("F"))) {
		    			check.setStore(article.get("F"));
		    			dataAdd = true;
		    		}
		    		if(!check.getPosition().equals(article.get("G"))) {
		    			check.setPosition(article.get("G"));
		    			dataAdd = true;
		    		}
		    		if(!check.getPhone().equals(article.get("H"))) {
		    			check.setPhone(article.get("H"));
		    			dataAdd = true;
		    		}
		    		//날짜 비교는 Timestamp로 비교한다.
		    		Timestamp ts = new Timestamp(transFormat.parse(article.get("I")).getTime());
		    		if(!check.getLastpurchase().equals(ts)){
		    			check.setLastpurchase(ts);
			    		check.setStatus("1");
		    			dataAdd = true;
		    		}
		    		if(check.getTotalbuy() != Integer.parseInt(article.get("J"))) {
		    			check.setTotalbuy(Integer.parseInt(article.get("J")));
		    			dataAdd = true;
		    		}
		    		if(!check.getAlarmyn().equals(article.get("K"))) {
		    			check.setAlarmyn(article.get("K"));
		    			dataAdd = true;
		    		}
		    		if(check.getLastpurchase().before(cal.getTime())) {
		    			check.setStatus("9");
		    			dataAdd = true;
		    		}
		    		
		    		if(dataAdd) {
		    			updateRowCount++;
		    		}else {
		    			noupdateRowCount++;
		    		}
		    	}
		    	if(dataAdd) {
		    		check.setLastupdate(now);
		    		saveList.add(check);
		    	}
		    	
		    	currentStateCount++;
		    	if (totalRowCount == currentStateCount) {
		    		setCurrentState("C");
		    	}
		    }
		    userRepo.saveAll(saveList);
		}
		String log = "총 데이터:"+currentStateCount+"건 | "+
					"신규데이터:"+insertRowCount+"건 | "+
					"변경데이터:"+updateRowCount+"건 | "+
					"미변경데이터:"+noupdateRowCount+"건";
		return log;
	}

}
