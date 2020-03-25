package com.loccitane.schedule;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.ExcelDownService;
import com.loccitane.utils.KakaoService;
import com.loccitane.utils.SftpService;


@Component
public class Scheduler {
	@Autowired
	UserService service;
	@Autowired
	LogService logservice;
	@Autowired
	GradeService grservice;
	@Autowired
	CouponService cpservice;
	@Autowired
	KakaoService kakao;
	@Autowired
	ExcelDownService excel;
	@Autowired
	SftpService sftp;
	
	
	//@Scheduled(cron = "0 30 7 * * *")
	//@Scheduled(cron = "0 * * * * *")
	public void run() throws IOException {
		Date now = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd");
		String today = transFormat.format(now);
		//생일쿠폰 발행
		//birthdayCouponRunner();
		
		//등급업
		if(today.equals("04-01") || today.equals("07-01") || today.equals("10-01") || today.equals("01-01")) {
			//등급업 프로세스
			gradeUP();
			//현재등급 알림 프로세스
			nowGradeAlarm();
		}
		  
		//엑셀다운로드(사용자, 쿠폰, 사용자별쿠폰)
		//excelDown();
		
		//sftp로 파일 전송(사용자, 쿠폰, 사용자별쿠폰)
		//sftp.sendFile();
		
		//마지막 구매일 1년 지난 사용자 휴면처리
		//dormant();
		
		//소멸예정쿠폰 알림 발송
		//deleteCouponAlarm();
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}
	
	//소멸예정 쿠폰 리스트
	private void deleteCouponAlarm() {
		// 미사용 쿠폰 리스트조회
		List<Coupon> couponList = cpservice.getUnuseCoupon(); 
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		//소멸 예정 날짜 지정 
		cal.add(Calendar.DATE, 7);
		String alarmDate = sdf.format(cal.getTime());
		int cnt = 0;
		List<User> dupcheckList = new ArrayList<User>();
		for(Coupon c : couponList) {
			String endDate = sdf.format(c.getEnddate());
			if(endDate.equals(alarmDate)) {
				User user = service.userCheck(c.getUsercode());
				if(user.getAlarmyn().equals("Y")) {
					boolean dupcheck = false;
					for(User u : dupcheckList) {
						if(user.getUsercode().equals(u.getUsercode())) {
							dupcheck = true;
						}
					}
					//중복되지 않을경우만 카톡발송
					if(!dupcheck) {
						JSONObject result = KakaoService.post("10049", user, alarmDate);
						String status = (String) result.get("status");
						if(status.equals("OK")) {
							dupcheckList.add(user);
							cnt ++;
						}
					}
				}
			}
		}
		Log log = new Log();
		log.setUserid("system");
	    log.setUsername("system");
	    log.setLogkind("KAKAO");
	    log.setLogcontent("[소멸예정쿠폰안내] 수신자 : "+cnt+"명 ");
	    log.setLogdate(now);
	    logservice.saveLog(log);
	}

	private void dormant() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(now);
	    cal.add(Calendar.YEAR,  -1);
		List<User> users = service.findAll();
		List<User> saveUsers = new ArrayList<User>();
		for(User user : users) {
			if(user.getStatus().equals("1")) {
				if(user.getLastpurchase() != null && user.getLastpurchase().before(cal.getTime())) {
					user.setStatus("9");
					user.setAlarmyn("N");
					saveUsers.add(user);
				}
			}
		}
		service.saveAll(saveUsers);
	}
	
	private void gradeUP() {
		Date now = new Date();
		try {
			//등급업 대상자 조회
			List<User> users = service.findGradeupList();
			List<Grade> grades = grservice.findAllAsc();
			int cnt = 0;
			for(User user : users)  {

				CouponMember cp = new CouponMember();
				//등급산정 (한번에 여러단계를 올라갈 수 있으니 등급 산정하기)
				for(Grade grade : grades) {
					if(user.getTotalbuy() >= Integer.parseInt(grade.getMinimum())) {
						user.setGrade(grade.getName());
					}
				}
				//등급업 저장
				service.saveUser(user);
				
				//쿠폰발행
				//시작날짜는 당일 0시0분0초로 세팅
				Calendar cl = Calendar.getInstance();
				cl.setTime(now);
				cl.set(Calendar.HOUR_OF_DAY, 0);
				cl.set(Calendar.MINUTE, 0);
				cl.set(Calendar.SECOND, 0);
				cp.setStartdate(cl.getTime());
				
				//종료날짜는 30일더한날짜로 세팅
				cl.add(Calendar.DATE, 30);
				cp.setEnddate(cl.getTime());
				cp.setUsercode(user.getUsercode());
				cp.setReason("GradeUp");
				
				if(user.getGrade().equals("PREMIUM")) {
					cp.setCpcode("LKRR53140E");
				}else if(user.getGrade().equals("LOYAL")) {
					cp.setCpcode("LKRR53150E");
				}else if(user.getGrade().equals("PRESTIGE")) {
					cp.setCpcode("LKRR53160E");
				}
				//등급에 맞는 쿠폰 발행
				cpservice.giveCoupon(cp, "system", cp.getReason());
				//카카오알림톡 전송
				if(user.getAlarmyn().equals("Y")) {
					JSONObject result = KakaoService.post("10028", user, "");
					String status = (String) result.get("status");
					if(status.equals("OK")) cnt ++;
				}
			}
			Log log = new Log();
			log.setUserid("system");
		    log.setUsername("system");
		    log.setLogkind("KAKAO");
		    log.setLogcontent("[등급업안내] 수신자 : "+cnt+"명 ");
		    log.setLogdate(now);
		    logservice.saveLog(log);
		}catch(Exception e) {
			// 시스템 로그저장
			Log log = new Log();
			log.setLogcontent("등급업 실패!!!");
			log.setLogdate(now);
			log.setLogkind("Scheduler");
			log.setUserid("system");
			log.setUsername("system");
			logservice.saveLog(log);
		}
	}

	public void birthdayCouponRunner() {
		Date now = new Date();
		try {
		//생일자 리스트
		List<User> birthdayList = service.getBirthdayList();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		
		//종료날짜는 30일더한날짜로 세팅
		cal.add(Calendar.DATE, 30);
		Date endDate = cal.getTime();
		
		for(int i=0; i<birthdayList.size(); i++) {
			CouponMember coupon = new CouponMember();
			coupon.setReason("Birthday");
			coupon.setUsercode(birthdayList.get(i).getUsercode());
			coupon.setCpcode("LKRMB10");
			coupon.setStartdate(startDate);
			coupon.setEnddate(endDate);
			
			cpservice.giveCoupon(coupon, "system", coupon.getReason());
		}
		
		}catch(Exception e) {
			// 시스템 로그저장
			Log log = new Log();
			log.setLogcontent("생일쿠폰발행오류");
			log.setLogdate(now);
			log.setLogkind("Scheduler");
			log.setUserid("system");
			log.setUsername("system");
			logservice.saveLog(log);
		}
		
	}
	public void excelDown() throws IOException {
		excel.excelDown("user");
		excel.excelDown("coupon");
		excel.excelDown("coupontomember");

	}
	
	public void nowGradeAlarm() {
		List<User> users = service.findAll();
		String regExp = "^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
		int cnt = 0;
		for(User user : users) {
			//핸드폰번호가 10자리이상일경우 
			if(user.getPhone().matches(regExp)) {
				if(user.getAlarmyn().equals("Y")) {
					JSONObject result = KakaoService.post("10027", user, "");
					String status = (String) result.get("status");
					if(status.equals("OK")) cnt ++;
				}
			}
		}
		Date now = new Date();
		Log log = new Log();
		log.setUserid("system");
	    log.setUsername("system");
	    log.setLogkind("KAKAO");
	    log.setLogcontent("[현재등급안내] 수신자 : "+cnt+"명 ");
	    log.setLogdate(now);
	    logservice.saveLog(log);
		
	}

}
