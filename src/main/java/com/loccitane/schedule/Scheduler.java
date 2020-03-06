package com.loccitane.schedule;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
	@Autowired
	UserService service;
	@Autowired
	LogService logservice;
	@Autowired
	GradeService grservice;
	
	
	@Scheduled(cron = "0 * * * * *")
	public void run() {
		//생일쿠폰 발행
		//birthdayCouponRunner();
		
		//휴면처리 및 등급업
		//dormantAndGradeUP(); 
	}
	
	private void dormantAndGradeUP() {
		Date now = new Date();
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd");
			String today = transFormat.format(now);
			boolean gradeUpDay = false;
			//해당 날짜만 등급업이 된다.
			if(today.equals("04-01") || today.equals("07-01") || today.equals("10-01") || today.equals("01-01")) {
				gradeUpDay = true;
			}
			//전체사용자 조회
			List<User> users = service.findAll();
			List<Grade> grades = new ArrayList<Grade>();
			if(gradeUpDay) {grades = grservice.findAllAsc();}
			Calendar cal = Calendar.getInstance();
		    cal.setTime(now);
		    cal.add(Calendar.YEAR,  -1);
			for(int i=0; i<users.size(); i++) {
				//사용자의 LASTPURCHASE 확인하여 1년이 지난경우 휴면으로 전환
				if(users.get(i).getLastpurchase() != null) {
					if(users.get(i).getLastpurchase().before(cal.getTime()) && users.get(i).getStatus().equals("1")) {
						users.get(i).setStatus("9");
					}
				}
				if(gradeUpDay) {
					for(int j=0; j<grades.size(); j++) {
						if(users.get(i).getTotalbuy() > grades.get(j).getMinimum()) {
							users.get(i).setGrade(grades.get(j).getName());
						}
					}
				}
			}
			service.saveAll(users);
		}catch(Exception e) {
			// 시스템 로그저장
			Log log = new Log();
			log.setLogcontent("휴면 처리 및 등급업 실패!!!");
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

}
