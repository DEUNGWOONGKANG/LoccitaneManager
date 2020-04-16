package com.loccitane.user.service;

import java.io.File;
import java.math.BigDecimal;
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

import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.log.domain.RefundLog;
import com.loccitane.log.service.LogService;
import com.loccitane.log.service.RefundLogService;
import com.loccitane.send.domain.Send;
import com.loccitane.send.service.SendService;
import com.loccitane.store.service.StoreService;
import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;
import com.loccitane.utils.ApiService;
import com.loccitane.utils.ExcelRead;
import com.loccitane.utils.ExcelReadOption;

@Service // 서비스 클래스임을 나타냄
public class UserService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	UserRepository userRepo;
	
	@Autowired
	LogService logservice;
	
	@Autowired
	CouponService cpservice;
	
	@Autowired
	StoreService storeservice;
	
	@Autowired
	ApiService api;
	
	@Autowired
	RefundLogService refund;
	
	@Autowired
	SendService sendservice;
	
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
		return userRepo.findAll();
	}
	
	// 사용자 usercode값으로 조회
	public User userCheck(String usercode) {
		return userRepo.findByUsercode(usercode);
	}
		
	// 사용자(고객) 로그인 
	public User userLogin(User user) {
		return userRepo.findByUsercodeAndPhoneEndingWith(user.getUsercode(), user.getPhone());
	}
	
	// 사용자 리스트 전화번호로 조회
	public List<User> getUserListByPhone(User user) {
		return userRepo.findAllByPhoneEndingWithOrderByStatusAsc(user.getPhone());
	}
	
	// 고객리스트 조회
	public Page<User> getCustomerList(Pageable pageable,String searchKey, String searchKeyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<User> userData = null;
		
		if(searchKey == null) {
			userData = userRepo.findAll(pageable);
		}else if(searchKey.equals("username")) {
			userData = userRepo.findAllByUsernameContaining(searchKeyword, pageable);
		}else if(searchKey.equals("phone")) {
			userData = userRepo.findAllByPhoneContaining(searchKeyword, pageable);
		}else if(searchKey.equals("usercode")) {
			userData = userRepo.findAllByUsercodeContaining(searchKeyword,  pageable);
		}else{
			userData = userRepo.findAll(pageable);
		}
		
		return userData;
	}
	
	//고객 검색
	public List<User> searchUserList(String searchKey, String searchKeyword){
		List<User> userData = null;
		
		if(searchKey.equals("username")) {
			userData = userRepo.findAllByUsername(searchKeyword);
		}else if(searchKey.equals("phone")) {
			userData = userRepo.findAllByPhone(searchKeyword);
		}else{
			userData = userRepo.findAllByUsercode(searchKeyword);
		}
		
		return userData;
		
	}
	
	//회원정보메뉴 등급 + 알람수신여부 저장
	public void saveUser(User user) {
		User userData  = userRepo.findByUsercode(user.getUsercode());
		Date now  = new Date();
		userData.setGrade(user.getGrade());
		userData.setAlarmyn(user.getAlarmyn());
		userData.setLastupdate(now);
		
		userRepo.save(userData);
	}
	
	//엑셀업로드 프로세스
	public String excelUpload(File destFile) throws Exception{
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		ExcelReadOption excelReadOption = new ExcelReadOption();
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H");
		excelReadOption.setStartRow(2);
		Date now = new Date();
		setCurrentState("A"); // 엑셀파일 읽어 오기 전 상태를 A
      
		List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);
		setTotalRowCount(excelContent.size());
		
		if(excelContent.size() > 0) {
			setCurrentState("B");
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(now);
		    cal.add(Calendar.YEAR,  -1);
		    List<User> uploadUser = new ArrayList<User>();
		    List<Send> sendList = new ArrayList<Send>();
		    for(Map<String, String> article: excelContent){
		    	User check = userCheck(article.get("A"));
		    	boolean dataAdd = false;
		    	BigDecimal totalbuy = new BigDecimal(article.get("F"));
		    	if(check == null) {
		    		//DB에 사용자가 없는 경우 신규 생성
		    		check = new User();
		    		check.setUsercode(article.get("A"));
		    		check.setUsername(article.get("B"));
		    		check.setBirthday(article.get("C"));
		    		check.setPhone(article.get("D"));
		    		check.setLastpurchase(transFormat.parse(article.get("E")));
		    		check.setTotalbuy(totalbuy);
		    		check.setAlarmyn(article.get("G"));
		    		check.setHomestore(article.get("H"));
		    		//기본등급은 REGULAR로 지정
		    		check.setGrade("REGULAR");
		    		check.setStartgrade("REGULAR");
		    		if(check.getLastpurchase().before(cal.getTime())) {
		    			check.setStatus("9");
		    		}else {
		    			check.setStatus("1");
		    		}
		    		check.setLastupdate(now);
		    		uploadUser.add(check);
		    		//관리자페이지에서 사용자 추가 API 호출
		    		api.userAddCall(check);
		    		insertRowCount++;
		    	}else{
		    		boolean apiCall = false;
		    		// 홈스토어 데이터를 가져와야 하므로 해당 컬럼부터 set
		    		if(check.getHomestore() == null) {
		    			if(article.get("H") != null) {
		    				check.setHomestore(article.get("H"));
		    				dataAdd = true;
		    			}
		    		}else{
		    			if(!check.getHomestore().equals(article.get("H"))) {
		    				check.setHomestore(article.get("H"));
		    				dataAdd = true;
		    			}
		    		}
		    		// DB에 사용자가 있는 경우 각 컬럼 체크하여 변경사항만 적용
		    		// 변경된 사항이 없을경우 UPDATE 하지 않음.
		    		if(!check.getUsercode().equals(article.get("A"))) {
		    			check.setUsercode(article.get("A"));
		    			dataAdd = true;
		    		}
		    		if(!check.getUsername().equals(article.get("B"))) {
		    			check.setUsername(article.get("B"));
		    			dataAdd = true;
		    		}
		    		if(check.getBirthday() == null) {
		    			if(article.get("C") != null) {
		    				check.setBirthday(article.get("C"));
			    			dataAdd = true;
		    			}
		    		}else{
		    			if(!check.getBirthday().equals(article.get("C"))) {
		    				check.setBirthday(article.get("C"));
		    				dataAdd = true;
		    			}
		    		}
		    		if(check.getPhone() == null) {
		    			if(article.get("D") != null) {
		    				check.setPhone(article.get("D"));
		    				dataAdd = true;
		    				apiCall = true;
		    			}
		    		}else{
		    			if(!check.getPhone().equals(article.get("D"))) {
		    				check.setPhone(article.get("D"));
		    				dataAdd = true;
		    				apiCall = true;
		    			}
		    		}
		    		//Lastpurchase 비교하기 (1년이 지난 사용자는 휴면상태로 전환되므로 체크하여 update해준다)
		    		//날짜 비교는 Timestamp로 비교한다.
		    		Timestamp ts = new Timestamp(transFormat.parse(article.get("E")).getTime());
		    		if(check.getLastpurchase() == null) {
		    			check.setLastpurchase(ts);
		    			dataAdd = true;
		    		}else {
		    			if(!check.getLastpurchase().equals(ts)){
			    			check.setLastpurchase(ts);
			    			dataAdd = true;
			    		}
		    		}
		    		
		    		//Totalbuy를 가지고 첫구매 확인 하여 첫구매 일 경우 쿠폰 발행
		    		if(totalbuy.compareTo(check.getTotalbuy()) != 0) {
		    			if(totalbuy.compareTo(check.getTotalbuy()) == 1){
		    				CouponMember cp = new CouponMember();
		    				CouponMember cp2 = new CouponMember();
		    				CouponMember sp = new CouponMember();
		    				Calendar cl = Calendar.getInstance();
		    				Calendar cl2 = Calendar.getInstance();
		    				boolean couponYn = false;
		    				boolean highgrade = false;
		    				cl.setTime(now);
		    				cl.set(Calendar.HOUR_OF_DAY, 0);
		    				cl.set(Calendar.MINUTE, 0);
		    				cl.set(Calendar.SECOND, 0);
		    				cl2.setTime(now);
		    				cl2.set(Calendar.HOUR_OF_DAY, 0);
		    				cl2.set(Calendar.MINUTE, 0);
		    				cl2.set(Calendar.SECOND, 0);
		    				
		    				cp.setStartdate(cl.getTime());
		    				cp.setReason("첫구매 감사쿠폰");
		    				sp.setStartdate(cl.getTime());
		    				sp.setReason("프리스티지 스페셜");
		    				if(check.getGrade().equals("REGULAR") && check.getSecond1date() == null){
		    					cp.setCpcode("LKRMR5000");
		    					//종료날짜는 90일더한날짜로 세팅
								cl.add(Calendar.DATE, 90);
								cp.setEnddate(cl.getTime());
								cp.setUsercode(check.getUsercode());
								check.setSecond1date(now);
								couponYn = true;
		    				}else if(check.getGrade().equals("PREMIUM") && check.getSecond2date() == null){
		    					cp.setCpcode("LKRMP10000");
		    					//종료날짜는 90일더한날짜로 세팅
								cl.add(Calendar.DATE, 90);
								cp.setEnddate(cl.getTime());
								cp.setUsercode(check.getUsercode());
								check.setSecond2date(now);
								couponYn = true;
		    				}else if(check.getGrade().equals("LOYAL") && check.getSecond3date() == null){
		    					cp.setCpcode("LKRML10000");
		    					//종료날짜는 180일더한날짜로 세팅
								cl.add(Calendar.DATE, 180);
								cp.setEnddate(cl.getTime());
								cp.setUsercode(check.getUsercode());
								check.setSecond3date(now);
								couponYn = true;
								highgrade = true;
								cp2.setStartdate(cl2.getTime());
			    				cp2.setReason("첫구매 감사쿠폰");
			    				cp2.setCpcode("LKRML20000");
			    				cp2.setEnddate(cl.getTime());
								cp2.setUsercode(check.getUsercode());
		    				}else if(check.getGrade().equals("PRESTIGE") && check.getSecond4date() == null){
		    					cp.setCpcode("LRKMP20000");
		    					//종료날짜는 180일더한날짜로 세팅
								cl.add(Calendar.DATE, 180);
								cp.setEnddate(cl.getTime());
								cp.setUsercode(check.getUsercode());
								check.setSecond4date(now);
								couponYn = true;
								highgrade = true;
								cp2.setStartdate(cl2.getTime());
			    				cp2.setReason("첫구매 감사쿠폰");
			    				cp2.setCpcode("LKRMP50000");
			    				cp2.setEnddate(cl.getTime());
								cp2.setUsercode(check.getUsercode());
		    				}
		    				//프리스티지 스페셜 부여 여부 체크 (기존 고객중 프리스티지고객만)
		    				if(check.getStartgrade().equals("PRESTIGE") && Integer.parseInt(article.get("F")) > 1000000 && check.getSpecial() == null) {
		    					Calendar ca = Calendar.getInstance();
		    					ca.setTime(now);
		    					ca.set(Calendar.HOUR_OF_DAY, 0);
		    					ca.set(Calendar.MINUTE, 0);
		    					ca.set(Calendar.SECOND, 0);
		    					sp.setStartdate(ca.getTime());
			    				sp.setReason("프리스티지 스페셜");
			    				
			    				sp.setCpcode("LKRR53170E");
		    					//종료날짜는 90일더한날짜로 세팅
			    				ca.add(Calendar.DATE, 90);
								sp.setEnddate(ca.getTime());
								sp.setUsercode(check.getUsercode());
								
								cpservice.giveCoupon(sp, "system", sp.getReason());
								
								Send send = new Send();
								send.setCreatedate(now);
								send.setDeletedate("");
								send.setGrade(check.getGrade());
								send.setHomestore(check.getHomestore());
								send.setSendtype("KAKAO");
								send.setTemplateid("10050");
								send.setUsercode(check.getUsercode());
								send.setUsername(check.getUsername());
								
								sendList.add(send);
								check.setSpecial(now);
		    				}
		    				if(couponYn) {
		    					Send cpsend = new Send();
		    					cpsend.setCreatedate(now);
		    					cpsend.setDeletedate("");
		    					cpsend.setGrade(check.getGrade());
		    					cpsend.setHomestore(check.getHomestore());
		    					cpsend.setSendtype("KAKAO");
		    					cpsend.setUsercode(check.getUsercode());
								cpsend.setUsername(check.getUsername());
								
								//쿠폰생성
		    					cpservice.giveCoupon(cp, "system", cp.getReason());
		    					//LOYAL, PRESTIGE의 경우 1개추가로 생성
		    					if(highgrade) cpservice.giveCoupon(cp2, "system", cp2.getReason());
	    						if(check.getGrade().equals("REGULAR") ||check.getGrade().equals("PREMIUM")) {
	    							cpsend.setTemplateid("10030");
	    						}else if(check.getGrade().equals("LOYAL") ||check.getGrade().equals("PRESTIGE")) {
	    							cpsend.setTemplateid("10031");
	    						}
	    						sendList.add(cpsend);
		    				}
		    			}else if(totalbuy.compareTo(check.getTotalbuy()) == -1) {
		    				RefundLog re = new RefundLog();
		    				re.setUsercode(check.getUsercode());
		    				re.setUsername(check.getUsername());
		    				re.setPasttotalbuy(check.getTotalbuy());
		    				re.setNowtotalbuy(totalbuy);
		    				re.setPastupdate(check.getLastupdate());
		    				re.setLastupdate(now);
		    				refund.saveLog(re);
		    			}
		    			check.setTotalbuy(totalbuy);
		    			dataAdd = true;
		    		}
		    		if(!check.getAlarmyn().equals(article.get("G"))) {
		    			check.setAlarmyn(article.get("G"));
		    			dataAdd = true;
		    		}
		    		
		    		if(check.getLastpurchase().after(cal.getTime()) && check.getStatus().equals("9")) {
		    			check.setStatus("1");
		    			check.setAlarmyn("Y");
		    			dataAdd = true;
		    			apiCall = true;
		    		}
		    		
		    		if(dataAdd) {
		    			updateRowCount++;
		    			check.setLastupdate(now);
		    			uploadUser.add(check);
		    			if(apiCall) api.userModifyCall(check);
		    			
		    		}else {
		    			noupdateRowCount++;
		    		}
		    	}
		    	currentStateCount++;
		    	if (totalRowCount == currentStateCount) {
		    		setCurrentState("C");
		    	}
		    }
		    userRepo.saveAll(uploadUser);
		    if(sendList.size() > 0) {
		    	sendservice.sendSaveAll(sendList);
		    }
		}
		String log = "총 데이터:"+currentStateCount+"건 | "+
					"신규데이터:"+insertRowCount+"건 | "+
					"변경데이터:"+updateRowCount+"건 | "+
					"미변경데이터:"+noupdateRowCount+"건";
		return log;
	}
	
	
	//생일자 검색하여 생일쿠폰 발행
	public List<User> getBirthdayList() {
		//생일 데이터가 존재하는 모든 사용자 가져오기
		SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd");
		List<String> grades = new ArrayList<String>();
		grades.add("PREMIUM");
		grades.add("LOYAL");
		grades.add("PRESTIGE");
		List<User> userList = userRepo.findAllByGradeInAndBirthdayIsNotNullAndStatus(grades, "1");
		//List<User> userList = userRepo.findAll();
		List<User> birthdayUser = new ArrayList<User>();
		Calendar now = Calendar.getInstance();
		// 15일 후 생일 체크
		now.add(Calendar.DAY_OF_MONTH,+15);
		Date birth = now.getTime();
//		now.add(Calendar.DAY_OF_MONTH,-1);
//		Date birth2 = now.getTime();
//		String birthcheck2 = transFormat.format(birth2);
//		now.add(Calendar.DAY_OF_MONTH,-1);
//		Date birth3 = now.getTime();
//		String birthcheck3 = transFormat.format(birth3);
		
		String birthcheck = transFormat.format(birth);
		for(User user : userList) {
			String userBirthday = user.getBirthday();
			if(userBirthday.length() > 5) {
				userBirthday = userBirthday.substring(userBirthday.length()-5, userBirthday.length());
				if(userBirthday.equals(birthcheck)) {
					birthdayUser.add(user);
				}
//				if(userBirthday.equals(birthcheck) || userBirthday.equals(birthcheck2)) {
//					birthdayUser.add(user);
//				}
			}
		}
		return birthdayUser;
	}
	
	public void saveAll(List<User> users) {
		userRepo.saveAll(users);
	}

	public List<User> getUserListByGrade(String grade) {
		return userRepo.findAllByGrade(grade);
	}
	
	public List<User> findGradeupList() {
		return userRepo.findAllByGradeAndTotalbuyGreaterThanEqualOrGradeAndTotalbuyGreaterThanEqualOrGradeAndTotalbuyGreaterThanEqual("REGULAR", 200000, "PREMIUM", 600000, "LOYAL", 1000000);
	}

	public List<User> findAllByStatus() {
		return userRepo.findAllByStatus("1");
	}


}
