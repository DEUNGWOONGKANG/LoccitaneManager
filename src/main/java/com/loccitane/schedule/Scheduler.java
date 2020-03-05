package com.loccitane.schedule;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
	@Autowired
	UserService service;
	@Autowired
	LogService logservice;
	
	@Scheduled(cron = "0 * * * * *")
	public void run() {
		//birthdayCouponRunner();
		//gradeUpRunner();
	}
	
	private void gradeUpRunner() {
		service.gradeUp();
	}

	public void birthdayCouponRunner() {
		Date now = new Date();
		try {
		//생일쿠폰발행
		service.birthdayCouponGive();
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
