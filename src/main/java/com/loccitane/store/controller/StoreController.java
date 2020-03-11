package com.loccitane.store.controller;


import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.store.domain.Store;
import com.loccitane.store.service.StoreService;
import com.loccitane.utils.Paging;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class StoreController {
	@Autowired
	StoreService service;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }
	
	//매장등록페이지 이동
	@GetMapping("/super/storeadd")
	public ModelAndView storeAdd() {
		ModelAndView nextView = new ModelAndView("super/superManagerStoreAdd");
		return nextView;
	}
	
	//관리자 로그인 시도
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
	
	//관리자 매장추가
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
	
	//관리자 매장 정보보기
	@GetMapping("/super/storeinfo/{storeseq}") 
	public ModelAndView superUserInfo(@PathVariable("storeseq") int storeseq){ 
		ModelAndView nextView = new ModelAndView("super/superManagerStoreInfo");
		
		Store storeData = service.getStoreInfo(storeseq);
		nextView.addObject("storeData", storeData);
	
		return nextView;
	}
}
