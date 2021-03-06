package com.loccitane.coupon.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.repository.CouponCoreRepository;
import com.loccitane.coupon.repository.CouponMemberRepository;
import com.loccitane.coupon.repository.CouponRepository;
import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;
import com.loccitane.utils.ApiService;

@Service // 서비스 클래스임을 나타냄
public class CouponService {
	
	@Autowired 
	CouponRepository couponRepo;
	@Autowired 
	CouponMemberRepository couponMemRepo;
	@Autowired 
	CouponCoreRepository couponCoreRepo;
	@Autowired 
	UserRepository userRepo;
	@Autowired
	ApiService apiservice;
	
	//사용가능한 모든 쿠폰 리스트 조회
	public List<CouponCore> getCouponList() {
		return couponCoreRepo.findAllByUseyn("Y");
	}
	
	//해당 고객의 쿠폰 데이터 조회
	public List<Coupon> getUserCoupon(String usercode) {
		return couponRepo.findAllByUsercode(usercode);
	}
	
	//사용처리할 쿠폰 찾기
	public CouponMember getCouponMemberInfo(int seq) {
		return couponMemRepo.findByCptmseq(seq);
	}
	
	//쿠폰 사용처리 및 삭제처리
	public void useCoupon(CouponMember coupon) {
		couponMemRepo.save(coupon);
	}
	
	//소멸예정 쿠폰 리스트 조회
	public List<CouponMember> getEndCoupon(Date date1, Date date2) {
		return couponMemRepo.findAllByUseynAndEnddateBetween("N", date1, date2);
	}
	
	//매장발행쿠폰 조회
	public List<Coupon> getStoreCptm() {
		return couponRepo.findAllByCreateuserNotOrderByCreatedateDesc("system");
	}
	
	//쿠폰 코어 데이터 저장
	public void couponSave(CouponCore couponCore) {
		couponCoreRepo.save(couponCore);
	}
	
	//쿠폰 상세보기시 정보가져오기
	public CouponCore getCouponInfo(int seq) {
		return couponCoreRepo.findBySeq(seq);
	}
	
	//관리자 고객 쿠폰 부여
	public void giveCoupon(CouponMember coupon, String Id, String reason_etc) {
		CouponMember newCoupon = new CouponMember();
		Date now  = new Date();
		
		Date end = coupon.getEnddate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		//유효기간 종료일의 시간은 23시 59분 59초로 세팅한다.
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		end = cal.getTime();
		
		String couponNum = "";
		while(true) {
			int n = 16; // n자리 쿠폰 
			char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
			Random rd = new Random();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				char ch = chs[rd.nextInt(chs.length)];
				sb.append(ch);
				if((i+1)%4 == 0 && i != 15) {
					sb.append("-");
				}
			}
			//난수 쿠폰번호 생성
			couponNum =  sb.toString();
			//중복되는 쿠폰번호가 있는지 체크
			CouponMember cp = couponMemRepo.findByCouponno(couponNum);
			
			if(cp == null) { //중복이없을 경우 진행
				break;
			}else {	//중복될 경우 다시 쿠폰번호 생성
				continue;
			}
		}
		String reason = "";
		if(coupon.getReason().equals("1")) {
			reason += "교환/환불";
			if(!reason_etc.equals("")) {
				reason += "["+reason_etc+"]";
			}
		}else if(coupon.getReason().equals("2")) {
			reason += "사용기한 만료";
			if(!reason_etc.equals("")) {
				reason += "["+reason_etc+"]";
			}
		}else if(coupon.getReason().equals("3")) {
			reason += "기타";
			if(!reason_etc.equals("")) {
				reason += "["+reason_etc+"]";
			}
		}else {
			reason += reason_etc;
		}
 		newCoupon.setReason(reason);
		newCoupon.setCouponno(couponNum);
		newCoupon.setCpcode(coupon.getCpcode());
		newCoupon.setUsercode(coupon.getUsercode());
		newCoupon.setCreatedate(now);
		newCoupon.setCreateuser(Id);
		newCoupon.setStartdate(coupon.getStartdate());
		newCoupon.setEnddate(end);
		newCoupon.setUseyn("N");
		
		couponMemRepo.save(newCoupon);
		//고객페이지 API호출하여 쿠폰생성
		apiservice.coupontomemberAddCall(newCoupon);
		
	}
		
	// 관리자 등급별 쿠폰 부여
	public void giveCouponToGrade(CouponMember coupon, Store loginUser, HttpServletRequest request) {
		String grade = request.getParameter("grade");
		List<User> userList = null;
		//등급별 사용자 리스트 추출
		if(grade.equals("ALL")) {
			userList = userRepo.findAllByStatus("1");
		}else {
			userList = userRepo.findAllByGradeAndStatus(grade, "1");
		}
		List<CouponMember> couponList = new ArrayList<CouponMember>();
		Date now  = new Date();
		
		Date end = coupon.getEnddate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.HOUR_OF_DAY, 23);
		cal.add(Calendar.MINUTE, 59);
		cal.add(Calendar.SECOND, 59);
		end = cal.getTime();
		
		for(int a = 0; a<userList.size(); a++) {
			CouponMember newCoupon = new CouponMember();
			//쿠폰번호 생성 시작
			String couponNum = "";
			while(true) {
				int n = 16; // n자리 쿠폰 
				char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
						'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
				Random rd = new Random();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < n; i++) {
					char ch = chs[rd.nextInt(chs.length)];
					sb.append(ch);
					if((i+1)%4 == 0 && i != 15) {
						sb.append("-");
					}
				}
				couponNum =  sb.toString();
				CouponMember cp = couponMemRepo.findByCouponno(couponNum);
				if(cp == null) {
					break;
				}else {
					continue;
				}
			}
			
			//쿠폰번호 생성 끝
			
			if(coupon.getReason().equals("1")) {
				newCoupon.setReason("교환/환불");
			}else if(coupon.getReason().equals("2")) {
				newCoupon.setReason("사용기한 만료");
			}else {
				newCoupon.setReason(request.getParameter("reason_etc"));
			}
			newCoupon.setCouponno(couponNum);
			newCoupon.setCpcode(coupon.getCpcode());
			newCoupon.setUsercode(userList.get(a).getUsercode());
			newCoupon.setCreatedate(now);
			newCoupon.setCreateuser(loginUser.getId());
			newCoupon.setStartdate(coupon.getStartdate());
			newCoupon.setEnddate(end);
			newCoupon.setUseyn("N");
			
			couponList.add(newCoupon);
			//고객페이지 API호출하여 쿠폰생성
			apiservice.coupontomemberAddCall(newCoupon);
		}
		
		couponMemRepo.saveAll(couponList);
		
	}
	
	//[슈퍼관리자]쿠폰관리페이지
	public Page<CouponCore> getAllCouponList(Pageable pageable, String searchKey, String searchKeyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<CouponCore> couponList = null;
        
        if(searchKey == null) {
        	couponList = couponCoreRepo.findAll(pageable);
		}else if(searchKey.equals("cpcode")) {
			couponList = couponCoreRepo.findAllByCpcodeContaining(searchKeyword, pageable);
		}else if(searchKey.equals("cpname")) {
			couponList = couponCoreRepo.findAllByCpnameContaining(searchKeyword, pageable);
		}else if(searchKey.equals("createuser")) {
			couponList = couponCoreRepo.findAllByCreateuserContaining(searchKeyword,  pageable);
		}else if(searchKey.equals("useyn")) {
			couponList = couponCoreRepo.findAllByUseyn(searchKeyword,  pageable);
		}else{
			couponList = couponCoreRepo.findAll(pageable);
		}
		
		return couponList;
	}
	
	//[슈퍼관리자]사용자별쿠폰관리페이지
	public Page<Coupon> getCouponToMemberList(Pageable pageable, String searchKey, String searchKeyword,String status) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<Coupon> couponList = null;
        if(status == null )status = "ALL";
        if(searchKey == null) {
        	if(status.equals("ALL")) {
        		couponList = couponRepo.findAll(pageable);
        	}else{
        		couponList = couponRepo.findAllByUsedyn(status, pageable);
        	}
		}else if(searchKey.equals("cpcode")) {
			if(status.equals("ALL")) {
				couponList = couponRepo.findAllByUsername(searchKeyword, pageable);
			}else{
				couponList = couponRepo.findAllByUsernameAndUsedyn(searchKeyword, status, pageable);
			}
		}else if(searchKey.equals("cpname")) {
			if(status.equals("ALL")) {
        		couponList = couponRepo.findAllByCpcode(searchKeyword, pageable);
        	}else{
        		couponList = couponRepo.findAllByCpcodeAndUsedyn(searchKeyword, status, pageable);
        	}
		}else if(searchKey.equals("createuser")) {
			if(status.equals("ALL")) {
        		couponList = couponRepo.findAllByCouponno(searchKeyword,  pageable);
        	}else{
        		couponList = couponRepo.findAllByCouponnoAndUsedyn(searchKeyword, status, pageable);
        	}
		}else{
			if(status.equals("ALL")) {
        		couponList = couponRepo.findAll(pageable);
        	}else{
        		couponList = couponRepo.findAllByUsedyn(status, pageable);
        	}
		}
		
		return couponList;
	}

	//[공통]통계 사용한 쿠폰 데이터 가져오기
	public List<Coupon> getUseCouponList(String id, Date startDate, Date endDate, String grade, String couponCode) {
		if(id.equals("ALL")) {
			if(grade.equals("ALL")) {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsedynAndUsedateBetween("Y", startDate, endDate);
				}else {
					return couponRepo.findAllByUsedynAndUsedateBetweenAndCpcode("Y", startDate, endDate, couponCode);
				}
			}else {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsedynAndUsedateBetweenAndGrade("Y", startDate, endDate, grade);
				}else {
					return couponRepo.findAllByUsedynAndUsedateBetweenAndGradeAndCpcode("Y", startDate, endDate, grade, couponCode);
				}
			}
		}else {
			if(grade.equals("ALL")) {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsedynAndUsemanagerAndUsedateBetween("Y", id, startDate, endDate);
				}else {
					return couponRepo.findAllByUsedynAndUsemanagerAndUsedateBetweenAndCpcode("Y", id, startDate, endDate, couponCode);
				}
			}else {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsedynAndUsemanagerAndUsedateBetweenAndGrade("Y", id, startDate, endDate, grade);
				}else {
					return couponRepo.findAllByUsedynAndUsemanagerAndUsedateBetweenAndGradeAndCpcode("Y", id, startDate, endDate, grade, couponCode);
				}
			}
		}
	}
	
	//[공통]통계 생성한 쿠폰 데이터 가져오기
	public List<Coupon> getCreateCouponList(String id, Date startDate, Date endDate, String grade, String couponCode) {
		if(id.equals("ALL")) {
			if(grade.equals("ALL")) {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByCreatedateBetween(startDate, endDate);
				}else {
					return couponRepo.findAllByCreatedateBetweenAndCpcode(startDate, endDate, couponCode);
				}
			}else {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByCreatedateBetweenAndGrade(startDate, endDate, grade);
				}else {
					return couponRepo.findAllByCreatedateBetweenAndGradeAndCpcode(startDate, endDate, grade, couponCode);
				}
			}
		}else {
			if(grade.equals("ALL")) {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsemanagerAndCreatedateBetween(id, startDate, endDate);
				}else {
					return couponRepo.findAllByUsemanagerAndCreatedateBetweenAndCpcode(id, startDate, endDate, couponCode);
				}
			}else {
				if(couponCode.equals("ALL")) {
					return couponRepo.findAllByUsemanagerAndCreatedateBetweenAndGrade(id, startDate, endDate, grade);
				}else {
					return couponRepo.findAllByUsemanagerAndCreatedateBetweenAndGradeAndCpcode(id, startDate, endDate, grade, couponCode);
				}
			}
		}
	}
}
