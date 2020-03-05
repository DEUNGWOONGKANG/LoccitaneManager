package com.loccitane.coupon.service;

import java.util.ArrayList;
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
import com.loccitane.coupon.domain.CouponMemberTemp;
import com.loccitane.coupon.domain.CouponTemp;
import com.loccitane.coupon.repository.CouponCoreRepository;
import com.loccitane.coupon.repository.CouponMemberRepository;
import com.loccitane.coupon.repository.CouponMemberTempRepository;
import com.loccitane.coupon.repository.CouponRepository;
import com.loccitane.coupon.repository.CouponTempRepository;
import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;
import com.loccitane.user.repository.UserRepository;

@Service // 서비스 클래스임을 나타냄
public class CouponService {
	
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponRepository couponRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponMemberRepository couponMemRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponCoreRepository couponCoreRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	UserRepository userRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponMemberTempRepository couponMemTempRepo;
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	CouponTempRepository coupontempRepo;
	
	//모든 쿠폰 리스트 조회
	public List<CouponCore> getCouponList() {
		List<CouponCore> couponData  = couponCoreRepo.findAllByUseyn("Y");
		return couponData;
	}
	
	//해당 고객의 쿠폰 데이터 조회
	public List<Coupon> getUserCoupon(String usercode) {
		List<Coupon> couponData  = couponRepo.findAllByUsercode(usercode);
		return couponData;
	}
	
	//쿠폰 사용처리
	public void useCoupon(String usercode, int seq, Store loginUser) {
		CouponMember coupon = couponMemRepo.findBySeq(seq);
		Date now  = new Date();
		coupon.setUsedate(now);
		coupon.setUseyn("Y");
		coupon.setUsemanager(loginUser.getId());
		couponMemRepo.save(coupon);
	}
	
	//관리자 고객 쿠폰 부여
	public void giveCoupon(CouponMember coupon, Store loginUser, HttpServletRequest request) {
		CouponMember newCoupon = new CouponMember();
		Date now  = new Date();
		
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
		String couponNum =  sb.toString();
		
		if(coupon.getReason().equals("1")) {
			newCoupon.setReason("교환/환불");
		}else if(coupon.getReason().equals("2")) {
			newCoupon.setReason("사용기한 만료");
		}else {
			newCoupon.setReason(request.getParameter("reason_etc"));
		}
		newCoupon.setCouponno(couponNum);
		newCoupon.setCpcode(coupon.getCpcode());
		newCoupon.setUsercode(coupon.getUsercode());
		newCoupon.setCreatedate(now);
		newCoupon.setCreateuser(loginUser.getId());
		newCoupon.setStartdate(coupon.getStartdate());
		newCoupon.setEnddate(coupon.getEnddate());
		newCoupon.setUseyn("N");
		
		couponMemRepo.save(newCoupon);
	}
	
	//관리자 고객 쿠폰 부여요청
	public void giveCouponRequest(CouponMemberTemp coupon, Store loginUser, HttpServletRequest request) {
		CouponMemberTemp newCoupon = new CouponMemberTemp();
		Date now  = new Date();
		
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
		String couponNum =  sb.toString();
		
		if(coupon.getReason().equals("1")) {
			newCoupon.setReason("교환/환불");
		}else if(coupon.getReason().equals("2")) {
			newCoupon.setReason("사용기한 만료");
		}else {
			newCoupon.setReason(request.getParameter("reason_etc"));
		}
		newCoupon.setCouponno(couponNum);
		newCoupon.setCpcode(coupon.getCpcode());
		newCoupon.setUsercode(coupon.getUsercode());
		newCoupon.setCreatedate(now);
		newCoupon.setCreateuser(loginUser.getId());
		newCoupon.setStartdate(coupon.getStartdate());
		newCoupon.setEnddate(coupon.getEnddate());
		newCoupon.setRequestyn("N");
		
		couponMemTempRepo.save(newCoupon);
	}
		
	// 관리자 등급별 쿠폰 부여
	public void giveCouponToGrade(CouponMember coupon, Store loginUser, HttpServletRequest request) {
		String grade = request.getParameter("grade");
		List<User> userList = null;
		//등급별 사용자 리스트 추출
		if(grade.equals("ALL")) {
			userList = userRepo.findAll();
		}else {
			userList = userRepo.findAllByGrade(grade);
		}
		List<CouponMember> couponList = new ArrayList<CouponMember>();
		Date now  = new Date();
		
		for(int a = 0; a<userList.size(); a++) {
			CouponMember newCoupon = new CouponMember();
			//쿠폰번호 생성 시작
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
			String couponNum =  sb.toString();
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
			newCoupon.setEnddate(coupon.getEnddate());
			newCoupon.setUseyn("N");
			
			couponList.add(newCoupon);
		}
		
		couponMemRepo.saveAll(couponList);
		
	}

	public Page<CouponCore> getAllCouponList(Pageable pageable, String searchKey, String searchKeyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<CouponCore> couponList = null;
        
        if(searchKey == null) {
        	couponList = couponCoreRepo.findAll(pageable);
		}else if(searchKey.equals("cpcode")) {
			couponList = couponCoreRepo.findAllByCpcode(searchKeyword, pageable);
		}else if(searchKey.equals("cpname")) {
			couponList = couponCoreRepo.findAllByCpname(searchKeyword, pageable);
		}else if(searchKey.equals("createuser")) {
			couponList = couponCoreRepo.findAllByCreateuser(searchKeyword,  pageable);
		}else if(searchKey.equals("useyn")) {
			couponList = couponCoreRepo.findAllByUseyn(searchKeyword,  pageable);
		}else{
			couponList = couponCoreRepo.findAll(pageable);
		}
		
		return couponList;
	}
	
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

	public void couponSave(CouponCore couponCore) {
		couponCoreRepo.save(couponCore);
	}

	public CouponCore getCouponInfo(int seq) {
		CouponCore couponData = couponCoreRepo.findBySeq(seq);
		
		return couponData;
	}
	
	public Page<CouponTemp> couponRequestList(Pageable pageable, String searchKey, String searchKeyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<CouponTemp> couponList = null;
        
        if(searchKey == null) {
        	couponList = coupontempRepo.findAll(pageable);
		}else if(searchKey.equals("cpcode")) {
			couponList = coupontempRepo.findAllByCpcode(searchKeyword, pageable);
		}else if(searchKey.equals("username")) {
			couponList = coupontempRepo.findAllByUsername(searchKeyword, pageable);
		}else if(searchKey.equals("createuser")) {
			couponList = coupontempRepo.findAllByCreateuser(searchKeyword,  pageable);
		}else{
			couponList = coupontempRepo.findAll(pageable);
		}
		
		return couponList;
	}

	//쿠폰 발행 승인처리
	public void couponApproval(int seq) {
		//TEMP에 있는 데이터 찾아서 승인완료로 변경
		CouponTemp coupon = coupontempRepo.findBySeq(seq);
		CouponMember newCoupon = new CouponMember();
		//신규쿠폰 생성하여 TEMP => 정상 DB로 이동
		newCoupon.setCouponno(coupon.getCouponno());
		newCoupon.setCpcode(coupon.getCpcode());
		newCoupon.setCreatedate(coupon.getIssueday());
		newCoupon.setCreateuser(coupon.getCreateuser());
		newCoupon.setEnddate(coupon.getEnddate());
		newCoupon.setReason(coupon.getReason());
		newCoupon.setStartdate(coupon.getStartdate());
		newCoupon.setUsercode(coupon.getUsercode());
		newCoupon.setUseyn("N");
		couponMemRepo.save(newCoupon);
		
		//저장 후 TEMP에 있는 데이터는 REQUESTYN을 Y로 변경하여 UPDATE
		coupon.setRequestyn("Y");
		coupontempRepo.save(coupon);
	}
	
	//모든 쿠폰 발행 승인처리
	public void allCouponApproval() {
		//TEMP에 있는 데이터 찾아서 승인완료로 변경
		List<CouponTemp> coupon = coupontempRepo.findAllByRequestyn("N");
		CouponMember newCoupon = new CouponMember();
		List<CouponMember> approvalCoupon = new ArrayList<CouponMember>();
		//신규쿠폰 생성하여 TEMP => 정상 DB로 이동
		for(int i=0; i<coupon.size(); i++) {
			newCoupon.setCouponno(coupon.get(i).getCouponno());
			newCoupon.setCpcode(coupon.get(i).getCpcode());
			newCoupon.setCreatedate(coupon.get(i).getIssueday());
			newCoupon.setCreateuser(coupon.get(i).getCreateuser());
			newCoupon.setEnddate(coupon.get(i).getEnddate());
			newCoupon.setReason(coupon.get(i).getReason());
			newCoupon.setStartdate(coupon.get(i).getStartdate());
			newCoupon.setUsercode(coupon.get(i).getUsercode());
			newCoupon.setUseyn("N");
			
			approvalCoupon.add(newCoupon);
			coupon.get(i).setRequestyn("Y");
		}
		couponMemRepo.saveAll(approvalCoupon);
		//저장 후 TEMP에 있는 데이터는 REQUESTYN을 Y로 변경하여 UPDATE
		coupontempRepo.saveAll(coupon);
	}

}
