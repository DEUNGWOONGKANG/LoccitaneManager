package com.loccitane.user.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.Paging;

@Controller // 이 클래스가 컨트롤러라는 것을 알려주는 어노테이션
public class UserController {
	@Autowired
	UserService service;
	@Autowired
	CouponService cpservice;
	@Autowired
	GradeService grservice;
	@Autowired
	LogService logservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }
	
	//최초 고객ID 체크 후 휴대폰번호 4자리 입력 후 로그인 체크
	@PostMapping("/user/login") 
	public ModelAndView loginUser(User user, HttpServletResponse response) throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/customerCouponList");
		User userData = service.userLogin(user); //해당 유저id값+전화번호 뒷자리4자리로 사용자 존재여부 확인
		if(userData == null) { //사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 고객 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}else { // 사용자가 존재하는 경우
			List<Coupon> couponList = cpservice.getUserCoupon(user.getUserid()); // 해당 사용자의 쿠폰리스트 조회
			nextView.addObject("userData", userData); //사용자데이터
			nextView.addObject("couponList", couponList); //쿠폰리스트
			nextView.addObject("tel", user.getPhone()); //핸드폰번호 뒤4자리
		}
		return nextView;
	}
	
	@GetMapping("/user/check/{userid}") // 최초 고객 URL 랜딩페이지에서 ID값 체크
	public ModelAndView checkUser(@PathVariable("userid") String userid){
		User user = service.userCheck(userid); // 서비스에서 요청에 해당하는 처리를 한다.
		ModelAndView nextView = new ModelAndView("jsp/customerMain");
		if(user == null) {
			//존재하지 않는 ID일경우 휴대폰 번호 입력란 DISABLED 처리
			nextView.addObject("check", "N");
		}else{
			//존재하는 ID일 경우 정상적으로 페이지 접근
			nextView.addObject("check", "Y");
			nextView.addObject("userid", userid);
		}
		return nextView;
	}
	
	//매장관리자 로그인 시도
	@PostMapping("/store/login") 
	public ModelAndView storeLogin(User user, HttpServletResponse response, HttpServletRequest request) throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/storeManagerCouponUse");
		User userData = service.managerLogin(user);// 매니저 로그인 DB체크
		
		if(userData == null) { //사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 사용자 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}else{
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("loginUser", userData);
			httpSession.setAttribute("menu", "menuli1");
		}
		return nextView;
	}
	
	
	//로그아웃
	@GetMapping("/manager/logout") 
	public ModelAndView logoutManager(){ 
		ModelAndView nextView = new ModelAndView("jsp/logout");
		
		return nextView;
	}
	
	//매장매니저 메뉴 클릭
	@GetMapping("/manager/{menu}") 
	public ModelAndView goMenu(@PathVariable("menu") String menu, HttpServletRequest request) throws Exception { 
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		User loginUser = (User) httpSession.getAttribute("loginUser");
		
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("jsp/logout");
		} else if(loginUser != null && menu.equals("menu1")) {
			nextView = new ModelAndView("jsp/storeManagerCouponUse");
			httpSession.setAttribute("menu", "menuli1");
		} else if(loginUser != null && menu.equals("menu2")){
			nextView = new ModelAndView("jsp/storeManagerCouponGive");
			List<CouponCore> couponList = cpservice.getCouponList();
			httpSession.setAttribute("menu", "menuli2");
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "N");

		} else {
			
		}
		
		return nextView;
	}
	
	//최초 로그인 페이지
	@GetMapping("/") 
	public ModelAndView main(){ 
		ModelAndView nextView = new ModelAndView("jsp/login");
		return nextView;
	}
	
	//매장 매니저 - 사용자 뒷번호로 검색
	@PostMapping("/manager/userSearch") 
	public ModelAndView getUserList(User user){
		ModelAndView nextView = new ModelAndView("jsp/storeManagerUserList");
		List<User> userData = service.getUserListByPhone(user);
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("searchPhone", user.getPhone()); //사용자데이터
		return nextView;
	}
	
	//매장 매니저 - 쿠폰부여시 사용자 검색
	@GetMapping("/manager/userSearch/{searchKey}/{searchKeyword}") 
	public ModelAndView searchUser(@PathVariable("searchKey") String searchKey
			,@PathVariable("searchKeyword") String searchKeyword ){
		List<User> userData = service.searchUserList(searchKey, searchKeyword);
		ModelAndView nextView = new ModelAndView("jsp/storeManagerUserListPop");
		nextView.addObject("userData", userData); //사용자데이터
		return nextView;
	}
	
	//슈퍼관리자 로그인 시도
	@PostMapping("/super/login") 
	public ModelAndView superLogin(User user, HttpServletResponse response, HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/superManagerMain");
		User userData = service.managerLogin(user);// 매니저 로그인 DB체크
		
		if(userData == null) { //사용자가 존재하지 않을경우
			response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('등록된 사용자 정보가 없습니다.'); history.go(-1);</script>");
            out.flush();
		}else {
			// 쿠폰사용시 관리자의 정보를 DB에 넣기위해 세션에 로그인 정보 저장
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("loginUser", userData);
			httpSession.setAttribute("menu", "menu1");
		}
		return nextView;
	}
	
	//슈퍼관리자 홈
	@GetMapping("/super/home") 
	public ModelAndView superHome(){ 
		ModelAndView nextView = new ModelAndView("jsp/superManagerMain");
		return nextView;
	}
	
	//슈퍼관리자 사용자 리스트
	@RequestMapping("/super/userlist") 
	public ModelAndView superUserList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
		ModelAndView nextView = new ModelAndView("jsp/superManagerUserList");
		
		String searchKey = request.getParameter("searchKey");
		String searchKeyword = request.getParameter("searchKeyword");
		
		Page<User> userList = null;
		Paging paging = new Paging();
		if(searchKey != null && searchKeyword != null) {
			userList = service.getCustomerList(pageable, searchKey, searchKeyword);
		}else {
			userList = service.getCustomerList(pageable);
		}
		int curPage = pageable.getPageNumber();
		if(curPage == 0) curPage = curPage + 1;
		paging.Pagination((int)userList.getTotalElements(), curPage);
		
		nextView.addObject("userList", userList);
		nextView.addObject("paging", paging);
		nextView.addObject("searchKey", searchKey);
		nextView.addObject("searchKeyword", searchKeyword);
	
		return nextView;
	}
	
	//슈퍼관리자 사용자 정보보기
	@GetMapping("/super/userinfo/{userid}") 
	public ModelAndView superUserInfo(@PathVariable("userid") String userid){ 
		ModelAndView nextView = new ModelAndView("jsp/superManagerUserInfo");
		
		User userData = service.userCheck(userid);
		List<Grade> gradeList = grservice.findAll();
		List<Coupon> couponList = cpservice.getUserCoupon(userid); // 해당 사용자의 쿠폰리스트 조회
		
		nextView.addObject("userData", userData);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("couponList", couponList);
		nextView.addObject("saveyn", "N");
	
		return nextView;
	}
	
	//슈퍼관리자 계정관리=>회원정보에서 수정시
	@PostMapping("/super/modifyuser") 
	public ModelAndView modifyUser(User user, HttpServletResponse response, HttpServletRequest request){ 
		ModelAndView nextView = superUserInfo(user.getUserid());
		service.saveUser(user);// 사용자 회원 등급 및 알람수신여부 저장
		nextView.addObject("saveyn", "Y");
		return nextView;
	}
	
	//슈퍼관리자 엑셀업로드
	@GetMapping("/super/excelupload")
	public ModelAndView superExcelUpload() {
		ModelAndView nextView = new ModelAndView("jsp/superManagerExcelUpload");
		List<Log> logList = logservice.getLog("EXCELUPLOAD");
		nextView.addObject("logList", logList);
		return nextView;
	}
	
    @RequestMapping(value = "/super/excelUploadAjax", method = RequestMethod.POST)
    public @ResponseBody ModelAndView excelUploadAjax(MultipartHttpServletRequest request)  throws Exception{
        MultipartFile excelFile =request.getFile("excelFile");
        System.out.println("엑셀 파일 업로드 컨트롤러");
        if(excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("엑셀파일을 선택 해 주세요.");
        }
        
        File destFile = new File("C:\\exceltemp\\"+excelFile.getOriginalFilename());
        try{
            excelFile.transferTo(destFile);
        }catch(IllegalStateException | IOException e){
            throw new RuntimeException(e.getMessage(),e);
        }
        
        String logContent = service.excelUpload(destFile);
        destFile.delete();
	    
	    Log log = new Log();
	    Date now = new Date();
	    HttpSession httpSession = request.getSession(true);
		User loginUser = (User) httpSession.getAttribute("loginUser");
		
	    log.setUserid(loginUser.getUserid());
	    log.setUsername(loginUser.getUsername());
	    log.setLogkind("EXCELUPLOAD");
	    log.setLogcontent(logContent);
	    log.setLogdate(now);
	    logservice.saveLog(log);
        
        ModelAndView nextView = new ModelAndView("jsp/superManagerExcelUpload");
        return nextView;
    }
    
    @ResponseBody
    @RequestMapping(value = "/excelUploadProgress", method = RequestMethod.POST)
    public String excelUploadProgress(HttpServletRequest request) throws Exception {
        int resultData = 0;
        
        if(UserService.getCurrentState()=="B"){
            int a =UserService.getCurrentStateCount();
            int b =UserService.getTotalRowCount();
            //ModuleServiceImpl.getCurrentStateCount()/ModuleServiceImpl.getTotalRowCount()*100
            
            
            BigDecimal aGd = new BigDecimal(a);
            BigDecimal bGd = new BigDecimal(b);
            
        
            resultData = aGd.divide(bGd, 2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue();
        }
        
        
        return Integer.toString(resultData);
    }
    @ResponseBody
    @RequestMapping(value = "/excelUploadProgressClear", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView excelUploadProgressClear(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    	UserService.setCurrentStateCount(0);
    	UserService.setTotalRowCount(0);
    	UserService.setUpdateRowCount(0);
        UserService.setNoupdateRowCount(0);
        UserService.setInsertRowCount(0);
        UserService.setCurrentState(null);
        
        return null;
    }
    
    //매장매니저 메뉴 클릭
  	@GetMapping("/super/menu/{menu}") 
  	public ModelAndView goAdminMenu(@PathVariable("menu") String menu, HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = null;
  		HttpSession httpSession = request.getSession(true);
  		User loginUser = (User) httpSession.getAttribute("loginUser");
  		
  		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
  			nextView = logoutManager();
  		} else if(loginUser != null && menu.equals("menu1")) {
  			nextView = superHome();
  		} else if(loginUser != null && menu.equals("menu2_1")){
  			nextView = superUserList(request, pageable);
  		} else if(loginUser != null && menu.equals("menu2_2")){
  			nextView = superExcelUpload();
  		} else if(loginUser != null && menu.equals("menu3")){
  		}
  		
  		httpSession.setAttribute("menu", menu);
  		return nextView;
  	}
	
}
