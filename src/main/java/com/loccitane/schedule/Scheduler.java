package com.loccitane.schedule;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.send.domain.Send;
import com.loccitane.send.service.SendService;
import com.loccitane.store.service.StoreService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.ApiService;


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
	StoreService store;
	@Autowired
	ApiService api;
	@Autowired
	SendService sendservice;
	
	//스케줄 날짜 설정
	@Scheduled(cron = "0 30 14 * * *")
	public void Schedule() {
		Date now = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd");
		String today = transFormat.format(now);
		//마지막 구매일 1년 지난 사용자 휴면처리
		dormant();
		
		//생일쿠폰 발행
		birthdayCouponRunner();
				
		//등급업
		if(today.equals("07-03") || today.equals("10-05") || today.equals("01-04")) {
			//등급업 프로세스
			gradeUP();
			//현재등급 알림 프로세스
			nowGradeAlarm();
		}
		//소멸예정쿠폰 알림 발송
		deleteCouponAlarm();
	}
	
	//소멸예정 쿠폰 알림 발송
	private void deleteCouponAlarm() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		//소멸 예정 기간 설정
		cal.add(Calendar.DAY_OF_MONTH, 7);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		Date date1 = cal.getTime();
		cal.set(Calendar.MILLISECOND, 999);
		Date date2 = cal.getTime();
		String alarmDate = sdf.format(cal.getTime());
		//소멸예정 쿠폰 리스트 조회
		List<CouponMember> couponList = cpservice.getEndCoupon(date1, date2); 
		//한번 발송한 사용자는 dupcheckList에 담아 중복사용자 체크를 한다. 
		List<User> dupcheckList = new ArrayList<User>();
		for(CouponMember c : couponList) {
			User user = service.userCheck(c.getUsercode());
			boolean dupcheck = false;
			for(User u : dupcheckList) {
				if(user.getUsercode().equals(u.getUsercode())) {
					dupcheck = true;
				}
			}
			//중복되지 않을경우만 카톡발송
			if(!dupcheck) {
				Send send = new Send();
				send.setCreatedate(now);
				send.setDeletedate(alarmDate);
				send.setGrade(user.getGrade());
				send.setHomestore(user.getHomestore());
				send.setSendtype("KAKAO");
				send.setTemplateid("10049");
				send.setUsercode(user.getUsercode());
				send.setUsername(user.getUsername());
				send.setBirthday(user.getBirthday());
				send.setPhone(user.getPhone());
				send.setTotalbuy(user.getTotalbuy());
				
				sendservice.sendSave(send);
				dupcheckList.add(user);
				//JSONObject result = KakaoService.post("10049", user, alarmDate, homestore);
				//String status = (String) result.get("status");
			}
		}
	}
	
	//마지막 구매일 1년 지난 사용자 휴면처리
	private void dormant() {
		System.out.println("====================휴면처리 시작====================");
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
					api.userModifyCall(user);
				}
			}
		}
		service.saveAll(saveUsers);
		System.out.println("====================휴면처리 종료====================");
	}
	
	//등급업 및 등급업 쿠폰 발행
	private void gradeUP() {
		Date now = new Date();
		try {
			//등급업 대상자 조회
			List<User> users = service.findGradeupList();
			List<Grade> grades = grservice.findAllAsc();
			for(User user : users)  {

				CouponMember cp = new CouponMember();
				//등급산정 (한번에 여러단계를 올라갈 수 있으니 등급 산정하기)
				for(Grade grade : grades) {
					BigDecimal minimum = new BigDecimal(grade.getMinimum());
					if(user.getTotalbuy().compareTo(minimum) == 0 || user.getTotalbuy().compareTo(minimum) == 1) {
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
				Send send = new Send();
				send.setCreatedate(now);
				send.setDeletedate("");
				send.setGrade(user.getGrade());
				send.setHomestore(user.getHomestore());
				send.setSendtype("KAKAO");
				send.setTemplateid("10028");
				send.setUsercode(user.getUsercode());
				send.setUsername(user.getUsername());
				send.setBirthday(user.getBirthday());
				send.setPhone(user.getPhone());
				send.setTotalbuy(user.getTotalbuy());
				
				sendservice.sendSave(send);
			}
		}catch(Exception e) {
			// 시스템 로그저장
			Log log = new Log();
			log.setLogcontent("등급업 실패!!!"+e.getMessage());
			log.setLogdate(now);
			log.setLogkind("Scheduler");
			log.setUserid("system");
			log.setUsername("system");
			logservice.saveLog(log);
		}
	}
	
	//생일 쿠폰 발행
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
			for(User user : birthdayList) {
				CouponMember coupon = new CouponMember();
				coupon.setReason("Birthday");
				coupon.setUsercode(user.getUsercode());
				coupon.setCpcode("LKRMB10");
				coupon.setStartdate(startDate);
				coupon.setEnddate(endDate);
				
				cpservice.giveCoupon(coupon, "system", coupon.getReason());
				if(user.getAlarmyn().equals("Y")) {
					Send send = new Send();
					send.setCreatedate(now);
					send.setDeletedate("");
					send.setGrade(user.getGrade());
					send.setHomestore(user.getHomestore());
					send.setSendtype("LMS");
					send.setTemplateid("");
					send.setUsercode(user.getUsercode());
					send.setUsername(user.getUsername());
					send.setBirthday(user.getBirthday());
					send.setPhone(user.getPhone());
					send.setTotalbuy(user.getTotalbuy());
					
					sendservice.sendSave(send);
				}
			}
		}catch(Exception e) {
			// 시스템 로그저장
			Log log = new Log();
			log.setLogcontent("생일쿠폰발행오류::"+e.getMessage());
			log.setLogdate(now);
			log.setLogkind("Scheduler");
			log.setUserid("system");
			log.setUsername("system");
			logservice.saveLog(log);
		}
		
	}
	
	//현재등급 알림 프로세스
	public void nowGradeAlarm() {
		List<User> users = service.findAllByStatus();
		//핸드폰번호 유효성 체크
		String regExp = "^01(?:0|1[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
		Date now = new Date();
		for(User user : users) {
			if(user.getPhone().matches(regExp)) {
				Send send = new Send();
				send.setCreatedate(now);
				send.setDeletedate("");
				send.setGrade(user.getGrade());
				send.setHomestore(user.getHomestore());
				send.setSendtype("KAKAO");
				send.setTemplateid("10027");
				send.setUsercode(user.getUsercode());
				send.setUsername(user.getUsername());
				send.setBirthday(user.getBirthday());
				send.setPhone(user.getPhone());
				send.setTotalbuy(user.getTotalbuy());
				
				sendservice.sendSave(send);
			}
		}
	}
}
