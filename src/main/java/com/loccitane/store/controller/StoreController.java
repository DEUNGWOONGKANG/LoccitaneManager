package com.loccitane.store.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.store.domain.Store;
import com.loccitane.store.service.StoreService;
import com.loccitane.utils.Paging;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class StoreController {
	@Autowired
	StoreService service;
	@Autowired
	CouponService cpservice;
	@Autowired
	GradeService grservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }
	
	//[슈퍼관리자] 매장등록페이지 이동
	@GetMapping("/super/storeadd")
	public ModelAndView storeAdd() {
		ModelAndView nextView = new ModelAndView("super/superManagerStoreAdd");
		return nextView;
	}
	
	//[공통] 관리자 로그인 시도
	@RequestMapping("/store/login") 
	public ModelAndView storeLogin(HttpServletResponse response, HttpServletRequest request) throws Exception { 
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String type = request.getParameter("type");
		Store storeData = service.managerLogin(loginId, loginPw, type);
		ModelAndView nextView = null;
		if(storeData == null) { 
			//사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 계정 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}else{
			//사용자가 존재할 경우
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("loginUser", storeData);
			
			if(type.equals("super")) {
				//슈퍼관리자
				nextView = new ModelAndView("super/superManagerMain");
			}else{
				//매장관리자
				nextView = new ModelAndView("store/storeManagerCouponUse");
				httpSession.setAttribute("menu", "menuli1");
			}
		}
		return nextView;
	}
	
	//[슈퍼관리자] 매장추가
	@PostMapping("/super/storesave") 
	public ModelAndView storeSave(Store store, HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
		store.setId(store.getCode());
		service.storeSave(store);
		
		ModelAndView nextView = new ModelAndView("super/superManagerStoreList");
		
		Page<Store> storeList = service.getStoreList(pageable, null, "");
		Paging paging = new Paging();
	  	if(storeList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)storeList.getTotalElements(), curPage);
  		}
		nextView.addObject("saveyn", "Y");
		nextView.addObject("storeList", storeList);
  		nextView.addObject("paging", paging);
  		
		return nextView;
	}
	//[매장관리자] 시간별 사용량 
	@RequestMapping(value = "/store/resulttotime", method = RequestMethod.POST) 
	public ModelAndView resultToTime(HttpServletRequest request) throws ParseException{ 
		ModelAndView nextView = new ModelAndView("store/storeManagerResultToTime");
		List<Integer> times = new ArrayList<Integer>();
		for(int i=0; i<24; i++) {
			times.add(i, 0);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = request.getParameter("startdate");
		Date srchday = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(srchday);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date endDate = cal.getTime();
		
		List<Coupon> useCouponList = cpservice.getUseCouponList(request.getParameter("id"),startDate,endDate,request.getParameter("grade"), "ALL");
		sdf = new SimpleDateFormat("HH");
		for(Coupon cm : useCouponList) {
			int time = Integer.parseInt(sdf.format(cm.getUsedate()));
			times.set(time, times.get(time)+1);
		}
		
		List<Grade> gradeList = grservice.getGradeUse();
		
		nextView.addObject("times", times);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("srchday", day);
		nextView.addObject("srchgrade", request.getParameter("grade"));
		return nextView;
	}
	//[매장관리자] 일자별 사용량 
	@RequestMapping(value = "/store/resulttoday", method = RequestMethod.POST) 
	public ModelAndView resultToDay(HttpServletRequest request) throws ParseException{ 
		ModelAndView nextView = new ModelAndView("store/storeManagerResultToDay");
		List<Grade> gradeList = grservice.getGradeUse();
		nextView.addObject("gradeList", gradeList);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		String day = request.getParameter("startdate");
		String srchgrade = request.getParameter("grade");
		String id = request.getParameter("id");
		Date srchday = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(srchday);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		
		// 해당 월의 날수
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DATE, daysOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date endDate = cal.getTime();
		
		nextView.addObject("srchday", day);
		nextView.addObject("srchgrade", srchgrade);
		
		List<Integer> dayUseCnt = new ArrayList<Integer>();
		List<Integer> dayCreateCnt = new ArrayList<Integer>();
		for(int i=0; i < daysOfMonth; i++) {
			dayUseCnt.add(i, 0);
			dayCreateCnt.add(i, 0);
		}
		
		// 쿠폰 사용량 가져오기
		List<Coupon> useCouponList = cpservice.getUseCouponList(id, startDate, endDate, srchgrade, "ALL");
		sdf = new SimpleDateFormat("dd");
		for(int j=0; j<useCouponList.size(); j++) {
			Coupon cm = useCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
			dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
			
			}
		// 쿠폰 발행량 가져오기
		List<Coupon> createCouponList = cpservice.getCreateCouponList(id, startDate, endDate, srchgrade, "ALL");
		for(int j=0; j<createCouponList.size(); j++) {
			Coupon cm = createCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
			dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
			}
		
		nextView.addObject("dayUseCnt", dayUseCnt);
		nextView.addObject("dayCreateCnt", dayCreateCnt);
		
		return nextView;
	}
	//[매장관리자] 쿠폰별 사용량 
	@RequestMapping(value = "/store/resulttocoupon", method = RequestMethod.POST) 
	public ModelAndView resultToCoupon(HttpServletRequest request) throws ParseException{ 
		ModelAndView nextView = new ModelAndView("store/storeManagerResultToCoupon");
		List<Grade> gradeList = grservice.getGradeUse();
		nextView.addObject("gradeList", gradeList);
		List<CouponCore> couponList = cpservice.getCouponList(); // 전체쿠폰리스트
		nextView.addObject("couponList", couponList);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		String day = request.getParameter("startdate");
		String srchgrade = request.getParameter("grade");
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		Date srchday = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(srchday);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		
		// 해당 월의 날수
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DATE, daysOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date endDate = cal.getTime();
		
		nextView.addObject("srchday", day);
		nextView.addObject("srchgrade", srchgrade);
		
		List<Integer> dayUseCnt = new ArrayList<Integer>();
		List<Integer> dayCreateCnt = new ArrayList<Integer>();
		for(int i=0; i < daysOfMonth; i++) {
			dayUseCnt.add(i, 0);
			dayCreateCnt.add(i, 0);
		}
		
		// 쿠폰 사용량 가져오기
		List<Coupon> useCouponList = cpservice.getUseCouponList(id, startDate, endDate, srchgrade, code);
		sdf = new SimpleDateFormat("dd");
		for(int j=0; j<useCouponList.size(); j++) {
			Coupon cm = useCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
			dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
			
			}
		// 쿠폰 발행량 가져오기
		List<Coupon> createCouponList = cpservice.getCreateCouponList(id, startDate, endDate, srchgrade, code);
		for(int j=0; j<createCouponList.size(); j++) {
			Coupon cm = createCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
			dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
			}
		
		nextView.addObject("dayUseCnt", dayUseCnt);
		nextView.addObject("dayCreateCnt", dayCreateCnt);
		
		return nextView;
	}
	
	//[슈퍼관리자] 매장 정보보기
	@GetMapping("/super/storeinfo/{storeseq}") 
	public ModelAndView superStoreInfo(@PathVariable("storeseq") int storeseq){ 
		ModelAndView nextView = new ModelAndView("super/superManagerStoreInfo");
		
		Store storeData = service.getStoreInfo(storeseq);
		nextView.addObject("storeData", storeData);
	
		return nextView;
	}
	
	//[매장관리자] 비밀번호변경 페이지
	@GetMapping("/store/modify") 
	public ModelAndView storePassword(HttpServletRequest request){ 
		ModelAndView nextView = new ModelAndView("store/storeManagerPasswordModify");
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("menu", "pw");
		return nextView;
	}
	
	//[슈퍼관리자] 비밀번호변경 페이지
	@GetMapping("/super/modify") 
	public ModelAndView superPassword(HttpServletRequest request){ 
		ModelAndView nextView = new ModelAndView("super/superManagerPasswordModify");
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("menu", "pw");
		return nextView;
	}

	//[공통] 비밀번호변경
	@RequestMapping("/passwordModify")
	public ModelAndView passwordModify(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
		ModelAndView nextView = null;
		String oldPw = request.getParameter("oldpw");
		String newPw = request.getParameter("newpw");
		String type = request.getParameter("type");
		if(type.equals("store")) {
			nextView = new ModelAndView("store/storeManagerPasswordModify");
		}else if(type.equals("super")) {
			nextView = new ModelAndView("super/superManagerPasswordModify");
		}
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		Store store = service.getStoreData(loginUser.getCode(), oldPw);
		if(store == null) {
			nextView.addObject("saveyn", "N");
		}else{
			store.setPw(newPw);
			service.storeSave(store);
			nextView.addObject("saveyn", "Y");
		}
		
		httpSession.setAttribute("menu", "pw");
		return nextView;
	}
}
