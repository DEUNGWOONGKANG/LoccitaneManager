package com.loccitane.user.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.loccitane.log.domain.RefundLog;
import com.loccitane.log.service.LogService;
import com.loccitane.log.service.RefundLogService;
import com.loccitane.send.domain.Send;
import com.loccitane.send.service.SendService;
import com.loccitane.store.domain.Store;
import com.loccitane.store.service.StoreService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
import com.loccitane.utils.ApiService;
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
	@Autowired
	StoreService storeservice;
	@Autowired
	ApiService apiservice;
	@Autowired
	RefundLogService refundservice;
	@Autowired
	SendService sendservice;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }
	
	//로그아웃
	@GetMapping("/logout") 
	public ModelAndView logoutManager(){ 
		ModelAndView nextView = new ModelAndView("logout");
		
		return nextView;
	}
	
	//매장매니저 메뉴 클릭
	@GetMapping("/store/{menu}") 
	public ModelAndView goMenu(@PathVariable("menu") String menu, HttpServletRequest request) throws Exception { 
		ModelAndView nextView = null;
		HttpSession httpSession = request.getSession(true);
		Store loginUser = (Store) httpSession.getAttribute("loginUser");
		
		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
			nextView = new ModelAndView("logout");
		} else if(loginUser != null && menu.equals("menu1")) {
			nextView = new ModelAndView("store/storeManagerCouponUse");
			httpSession.setAttribute("menu", "menuli1");
		} else if(loginUser != null && menu.equals("menu2")){
			nextView = new ModelAndView("store/storeManagerCouponGive");
			List<CouponCore> couponList = cpservice.getCouponList();
			httpSession.setAttribute("menu", "menuli2");
			nextView.addObject("couponList", couponList);
			nextView.addObject("giveyn", "N");
		} else if(loginUser != null && menu.equals("menu3")){
			nextView = new ModelAndView("store/storeManagerResultToTime");
			httpSession.setAttribute("menu", "menuli3");
			List<Grade> gradeList = grservice.getGradeUse();
			nextView.addObject("gradeList", gradeList);
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date startDate = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			Date endDate = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String day = sdf.format(now);
			nextView.addObject("srchday", day);
			nextView.addObject("srchgrade", "ALL");
			
			List<Integer> times = new ArrayList<Integer>();
			for(int i=0; i<24; i++) {
				times.add(i, 0);
			}
			
			List<Coupon> useCouponList = cpservice.getUseCouponList(loginUser.getId(), startDate, endDate, "ALL", "ALL");
			sdf = new SimpleDateFormat("HH");
			for(Coupon cm : useCouponList) {
				int time = Integer.parseInt(sdf.format(cm.getUsedate()));
				times.set(time, times.get(time)+1);
			}
			nextView.addObject("times", times);
		}else if(loginUser != null && menu.equals("menu4")){
			nextView = new ModelAndView("store/storeManagerResultToDay");
			httpSession.setAttribute("menu", "menuli4");
			List<Grade> gradeList = grservice.getGradeUse();
			nextView.addObject("gradeList", gradeList);
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String day = sdf.format(now);
			nextView.addObject("srchday", day);
			nextView.addObject("srchgrade", "ALL");
			
			List<Integer> dayUseCnt = new ArrayList<Integer>();
			List<Integer> dayCreateCnt = new ArrayList<Integer>();
			for(int i=0; i < daysOfMonth; i++) {
				dayUseCnt.add(i, 0);
				dayCreateCnt.add(i, 0);
			}
			// 쿠폰 사용량 가져오기
			List<Coupon> useCouponList = cpservice.getUseCouponList(loginUser.getId(), startDate, endDate, "ALL", "ALL");
			sdf = new SimpleDateFormat("dd");
			for(int j=0; j<useCouponList.size(); j++) {
				Coupon cm = useCouponList.get(j);
				int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
				dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
				
				}
			// 쿠폰 발행량 가져오기
			List<Coupon> createCouponList = cpservice.getCreateCouponList(loginUser.getId(), startDate, endDate, "ALL", "ALL");
			for(int j=0; j<createCouponList.size(); j++) {
				Coupon cm = createCouponList.get(j);
				int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
				dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
				}
			
			nextView.addObject("dayUseCnt", dayUseCnt);
			nextView.addObject("dayCreateCnt", dayCreateCnt);
		}else if(loginUser != null && menu.equals("menu5")){
			nextView = new ModelAndView("store/storeManagerResultToCoupon");
			httpSession.setAttribute("menu", "menuli5");
			List<Grade> gradeList = grservice.getGradeUse();
			List<CouponCore> couponList = cpservice.getCouponList(); // 전체쿠폰리스트
			nextView.addObject("gradeList", gradeList);
			nextView.addObject("couponList", couponList);
			
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String day = sdf.format(now);
			nextView.addObject("srchday", day);
			nextView.addObject("srchgrade", "ALL");
			
			List<Integer> dayUseCnt = new ArrayList<Integer>();
			List<Integer> dayCreateCnt = new ArrayList<Integer>();
			for(int i=0; i < daysOfMonth; i++) {
				dayUseCnt.add(i, 0);
				dayCreateCnt.add(i, 0);
			}
			// 쿠폰 사용량 가져오기
			List<Coupon> useCouponList = cpservice.getUseCouponList(loginUser.getId(), startDate, endDate, "ALL", "ALL");
			sdf = new SimpleDateFormat("dd");
			for(int j=0; j<useCouponList.size(); j++) {
				Coupon cm = useCouponList.get(j);
				int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
				dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
				
				}
			// 쿠폰 발행량 가져오기
			List<Coupon> createCouponList = cpservice.getCreateCouponList(loginUser.getId(), startDate, endDate, "ALL", "ALL");
			for(int j=0; j<createCouponList.size(); j++) {
				Coupon cm = createCouponList.get(j);
				int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
				dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
				}
			
			nextView.addObject("dayUseCnt", dayUseCnt);
			nextView.addObject("dayCreateCnt", dayCreateCnt);
		}
		return nextView;
	}
	
	//최초 로그인 페이지
	@GetMapping("/") 
	public ModelAndView main(){ 
		ModelAndView nextView = new ModelAndView("login");
		return nextView;
	}
	
	//매장 매니저 - 사용자 뒷번호로 검색
	@PostMapping("/store/userSearch") 
	public ModelAndView getUserList(User user){
		ModelAndView nextView = new ModelAndView("store/storeManagerUserList");
		List<User> userData = service.getUserListByPhone(user);
		nextView.addObject("userData", userData); //사용자데이터
		nextView.addObject("searchPhone", user.getPhone()); //사용자데이터
		return nextView;
	}
	
	//매장 매니저 - 쿠폰부여시 사용자 검색
	@GetMapping("/store/userSearch/{searchKey}/{searchKeyword}") 
	public ModelAndView searchUser(@PathVariable("searchKey") String searchKey
			,@PathVariable("searchKeyword") String searchKeyword ){
		List<User> userData = service.searchUserList(searchKey, searchKeyword);
		ModelAndView nextView = new ModelAndView("store/storeManagerUserListPop");
		nextView.addObject("userData", userData); //사용자데이터
		return nextView;
	}
	
	//슈퍼관리자 홈
	@GetMapping("/super/home") 
	public ModelAndView superHome(){ 
		ModelAndView nextView = new ModelAndView("super/superManagerMain");
		return nextView;
	}
	
	//슈퍼관리자 사용자 리스트
	@RequestMapping("/super/userlist") 
	public ModelAndView superUserList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
		ModelAndView nextView = new ModelAndView("super/superManagerUserList");
		
		String searchKey = request.getParameter("searchKey");
		String searchKeyword = request.getParameter("searchKeyword");
		
		Page<User> userList = service.getCustomerList(pageable, searchKey, searchKeyword);
		Paging paging = new Paging();
		if(userList != null) {
			int curPage = pageable.getPageNumber();
			if(curPage == 0) curPage = curPage + 1;
			paging.Pagination((int)userList.getTotalElements(), curPage);
		}
		nextView.addObject("userList", userList);
		nextView.addObject("paging", paging);
		nextView.addObject("searchKey", searchKey);
		nextView.addObject("searchKeyword", searchKeyword);
	
		return nextView;
	}
	
	//슈퍼관리자 사용자 정보보기
	@GetMapping("/super/userinfo/{usercode}") 
	public ModelAndView superUserInfo(@PathVariable("usercode") String usercode){ 
		ModelAndView nextView = new ModelAndView("super/superManagerUserInfo");
		
		User userData = service.userCheck(usercode);
		List<Grade> gradeList = grservice.getGradeUse();
		List<Coupon> couponList = cpservice.getUserCoupon(usercode); // 해당 사용자의 쿠폰리스트 조회
		
		nextView.addObject("userData", userData);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("couponList", couponList);
		nextView.addObject("saveyn", "N");
	
		return nextView;
	}
	
	//슈퍼관리자 계정관리=>회원정보에서 수정시
	@PostMapping("/super/modifyuser") 
	public ModelAndView modifyUser(User user, HttpServletResponse response, HttpServletRequest request){ 
		ModelAndView nextView = superUserInfo(user.getUsercode());
		boolean apiCall = false;
		User updateData = service.userCheck(user.getUsercode());
		updateData.setGrade(user.getGrade());
		updateData.setAlarmyn(user.getAlarmyn());
		if(!user.getStatus().equals(updateData.getStatus())) {
			updateData.setStatus(user.getStatus());
			apiCall = true;
		}
		service.saveUser(updateData);// 사용자 회원 등급 및 알람수신여부 저장
		if(apiCall) apiservice.userModifyCall(updateData);
 		nextView.addObject("saveyn", "Y");
		return nextView;
	} 
	
	//슈퍼관리자 엑셀업로드시 아래 출력되는 리스트
	@GetMapping("/super/excelupload")
	public ModelAndView superExcelUpload() {
		ModelAndView nextView = new ModelAndView("super/superManagerExcelUpload");
		List<Log> logList = logservice.getLog("EXCELUPLOAD");
		nextView.addObject("logList", logList);
		return nextView;
	}
	
	//엑셀업로드 로직
	//C:exceltemp 폴더에 엑셀파일을 떨구고 데이터 화 시킨 후 삭제처리 
    @RequestMapping(value = "/super/excelUploadAjax", method = RequestMethod.POST)
    public @ResponseBody ModelAndView excelUploadAjax(MultipartHttpServletRequest request)  throws Exception{
        MultipartFile excelFile =request.getFile("excelFile");
        HttpSession httpSession = request.getSession(true);
        Store loginUser = (Store) httpSession.getAttribute("loginUser");
        if(excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("엑셀파일을 선택 해 주세요.");
        }
        Date now = new Date();
        Log log = new Log();
        String path = "C:\\exceltemp";
        File folder = new File(path);
        
        //exceltemp 폴더가 없을 경우 생성 후 진행
        if(!folder.exists()) {
        	folder.mkdir();
        }
        
        File destFile = new File(path+"\\"+excelFile.getOriginalFilename());
        try{
            excelFile.transferTo(destFile);
            String logContent = service.excelUpload(destFile);
            //모든 처리가 끝나면 엑셀파일은 삭제한다.
	        destFile.delete();
		    
		    log.setUserid(loginUser.getId());
		    log.setUsername(loginUser.getManagername());
		    log.setLogkind("EXCELUPLOAD");
		    log.setLogcontent(logContent);
		    log.setLogdate(now);
		    logservice.saveLog(log);
        
        }catch(IllegalStateException | IOException e){
        	log.setLogcontent("엑셀업로드 오류");
			log.setLogdate(now);
			log.setLogkind("EXCELUPLOAD");
			log.setUserid(loginUser.getId());
			log.setUsername(loginUser.getManagername());
			logservice.saveLog(log);
			throw new RuntimeException(e.getMessage(),e);
        }
        ModelAndView nextView = new ModelAndView("super/superManagerExcelUpload");
        return nextView;
    }
    
    //엑셀 업로드시 프로그레스바 사용
    @ResponseBody
    @RequestMapping(value = "/excelUploadProgress", method = RequestMethod.POST)
    public String excelUploadProgress(HttpServletRequest request) throws Exception {
        int resultData = 0;
        
        if(UserService.getCurrentState()=="B"){
            int a =UserService.getCurrentStateCount();
            int b =UserService.getTotalRowCount();
            //ModuleServiceImpl.getCurrentStateCount()/ModuleServiceImpl.getTotalRowCount()*100
            //System.err.println("a>>>>>>>>>>"+a);
            //System.err.println("b>>>>>>>>>>"+b);
            
            BigDecimal aGd = new BigDecimal(a);
            BigDecimal bGd = new BigDecimal(b);
            
        
            resultData = aGd.divide(bGd, 2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue();
            //System.err.println("resultData>>>>>>>>>>"+resultData);
        }
        
        
        return Integer.toString(resultData);
    }
    
    //엑셀 업로드 후 프로그레스바 클리어
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
    
    //슈퍼관리자 쿠폰 리스트
  	@RequestMapping("/super/couponlist") 
  	public ModelAndView superCouponList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = new ModelAndView("super/superManagerCouponList");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		
  		Page<CouponCore> couponList = cpservice.getAllCouponList(pageable, searchKey, searchKeyword);
  		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
  		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  	
  		return nextView;
  	}
  	
  	//슈퍼관리자 사용자별 쿠폰 리스트
  	@RequestMapping("/super/coupontomember")
  	public ModelAndView superCouponToMemberList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = new ModelAndView("super/superManagerCouponToMember");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		String status = request.getParameter("status");
  		
  		Page<Coupon> couponList = cpservice.getCouponToMemberList(pageable, searchKey, searchKeyword, status);
  		Paging paging = new Paging();
	  	if(couponList != null) {
	  		int curPage = pageable.getPageNumber();
	  		if(curPage == 0) curPage = curPage + 1;
	  		paging.Pagination((int)couponList.getTotalElements(), curPage);
  		}
  		nextView.addObject("couponList", couponList);
  		nextView.addObject("paging", paging);
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  	
  		return nextView;
  	}
    
  	//슈퍼관리자 매장 리스트
  	@RequestMapping("/super/storelist") 
  	public ModelAndView superStoreList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = new ModelAndView("super/superManagerStoreList");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		
  		Page<Store> storeList = storeservice.getStoreList(pageable, searchKey, searchKeyword);
  		Paging paging = new Paging();
  	  	if(storeList != null) {
  	  		int curPage = pageable.getPageNumber();
  	  		if(curPage == 0) curPage = curPage + 1;
  	  		paging.Pagination((int)storeList.getTotalElements(), curPage);
  		}
  		nextView.addObject("storeList", storeList);
  		nextView.addObject("paging", paging);
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  	
  		return nextView;
  	}
  	
    //슈퍼관리자 메뉴 클릭
  	@GetMapping("/super/menu/{menu}") 
  	public ModelAndView goAdminMenu(@PathVariable("menu") String menu, HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = null;
  		HttpSession httpSession = request.getSession(true);
  		Store loginUser = (Store) httpSession.getAttribute("loginUser");
  		
  		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
  			nextView = logoutManager();
  		}else if(loginUser != null && menu.equals("menu1")){
  			nextView = superHome();
  		}else if(loginUser != null && menu.equals("menu2_1")){
  			nextView = superUserList(request, pageable);
  		}else if(loginUser != null && menu.equals("menu2_2")){
  			nextView = superExcelUpload();
  		}else if(loginUser != null && menu.equals("menu2_3")){
  			nextView = superRefundList(request, pageable);
  		}else if(loginUser != null && menu.equals("menu3")){
  			nextView = superStoreList(request, pageable);
  		}else if(loginUser != null && menu.equals("menu4_1")){
  			nextView = superCouponList(request, pageable);
  		}else if(loginUser != null && menu.equals("menu4_2")){
  			nextView = superCouponPublish();
  		}else if(loginUser != null && menu.equals("menu4_3")){
  			nextView = superCouponToMemberList(request, pageable);
  		}else if(loginUser != null && menu.equals("menu5_1")){
  			nextView = superResultToTime(request);
  		}else if(loginUser != null && menu.equals("menu5_2")){
  			nextView = superResultToDay(request);
  		}else if(loginUser != null && menu.equals("menu5_3")){
  			nextView = superResultToCoupon(request);
  		}else if(loginUser != null && menu.equals("menu6")){
  			nextView = superGradeList();
  		}else if(loginUser != null && menu.equals("menu7_1")){
  			nextView = superSendList(pageable);
  		}else if(loginUser != null && menu.equals("menu7_2")){
  			nextView = superKakaoList(request, pageable);
  		}
  		httpSession.setAttribute("menu", menu);
  		return nextView;
  	}
  	
  	//슈퍼관리자 사용자 리스트
  	@RequestMapping("/super/refundlist") 
  	public ModelAndView superRefundList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = new ModelAndView("super/superManagerRefundList");
  		
  		String searchKey = request.getParameter("searchKey");
  		String searchKeyword = request.getParameter("searchKeyword");
  		
  		Page<RefundLog> list = refundservice.getLog(pageable, searchKey, searchKeyword);
  		Paging paging = new Paging();
  		if(list != null) {
  			int curPage = pageable.getPageNumber();
  			if(curPage == 0) curPage = curPage + 1;
  			paging.Pagination((int)list.getTotalElements(), curPage);
  		}
  		nextView.addObject("refund", list);
  		nextView.addObject("paging", paging); 
  		nextView.addObject("searchKey", searchKey);
  		nextView.addObject("searchKeyword", searchKeyword);
  	
  		return nextView;
  	}

	//슈퍼관리자 시간별 사용량
  	@RequestMapping("/super/resulttotime") 
  	private ModelAndView superResultToTime(HttpServletRequest request) throws ParseException {
  		ModelAndView nextView = new ModelAndView("super/superManagerResultToTime");
		List<Grade> gradeList = grservice.getGradeUse();
		List<Store> storeList = storeservice.geAllStoreList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		String day = request.getParameter("startdate");
		if(day == null) {
			day = sdf.format(now);
		}
		Date srchday = sdf.parse(day);
		cal.setTime(srchday);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date startDate = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date endDate = cal.getTime();
		List<Integer> times = new ArrayList<Integer>();
		for(int i=0; i<24; i++) {
			times.add(i, 0);
		}
		String shop = request.getParameter("shop");
		String grade = request.getParameter("grade");
		if(shop == null) shop = "ALL";
		if(grade == null) grade = "ALL";
		List<Coupon> useCouponList = cpservice.getUseCouponList(shop, startDate, endDate, grade, "ALL");
		sdf = new SimpleDateFormat("HH");
		for(Coupon cm : useCouponList) {
			int time = Integer.parseInt(sdf.format(cm.getUsedate()));
			times.set(time, times.get(time)+1);
		}
		nextView.addObject("srchday", day);
		nextView.addObject("srchid", shop);
		nextView.addObject("srchgrade", grade);
		nextView.addObject("times", times);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("storeList", storeList);
		return nextView;
	}
  	
  	//슈퍼관리자 일자별 사용량
  	@RequestMapping("/super/resulttoday") 
  	private ModelAndView superResultToDay(HttpServletRequest request) throws ParseException {
  		ModelAndView nextView = new ModelAndView("super/superManagerResultToDay");
		List<Grade> gradeList = grservice.getGradeUse();
		List<Store> storeList = storeservice.geAllStoreList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		String day = request.getParameter("startdate");
		if(day == null) {
			day = sdf.format(now);
		}
		Date srchday = sdf.parse(day);
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
		String shop = request.getParameter("shop");
		String grade = request.getParameter("grade");
		if(shop == null) shop = "ALL";
		if(grade == null) grade = "ALL";
		
		List<Integer> dayUseCnt = new ArrayList<Integer>();
		List<Integer> dayCreateCnt = new ArrayList<Integer>();
		for(int i=0; i < daysOfMonth; i++) {
			dayUseCnt.add(i, 0);
			dayCreateCnt.add(i, 0);
		}
		
		// 쿠폰 사용량 가져오기
		List<Coupon> useCouponList = cpservice.getUseCouponList(shop, startDate, endDate, grade, "ALL");
		sdf = new SimpleDateFormat("dd");
		for(int j=0; j<useCouponList.size(); j++) {
			Coupon cm = useCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
			dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
			
			}
		// 쿠폰 발행량 가져오기
		List<Coupon> createCouponList = cpservice.getCreateCouponList(shop, startDate, endDate, grade, "ALL");
		for(int j=0; j<createCouponList.size(); j++) {
			Coupon cm = createCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
			dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
			}
		
		nextView.addObject("dayUseCnt", dayUseCnt);
		nextView.addObject("dayCreateCnt", dayCreateCnt);
		nextView.addObject("srchday", day);
		nextView.addObject("srchid", shop);
		nextView.addObject("srchgrade", grade);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("storeList", storeList);
		return nextView;
	}
  	
  	//슈퍼관리자 쿠폰별 사용량
  	@RequestMapping("/super/resulttocoupon") 
  	private ModelAndView superResultToCoupon(HttpServletRequest request) throws ParseException {
  		ModelAndView nextView = new ModelAndView("super/superManagerResultToCoupon");
		List<Grade> gradeList = grservice.getGradeUse();
		List<Store> storeList = storeservice.geAllStoreList();
		List<CouponCore> couponList = cpservice.getCouponList(); // 전체쿠폰리스트
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		String day = request.getParameter("startdate");
		if(day == null) {
			day = sdf.format(now);
		}
		Date srchday = sdf.parse(day);
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
		String shop = request.getParameter("shop");
		String grade = request.getParameter("grade");
		String code = request.getParameter("code");
		if(shop == null) shop = "ALL";
		if(grade == null) grade = "ALL";
		if(code == null) code = "ALL";
		
		List<Integer> dayUseCnt = new ArrayList<Integer>();
		List<Integer> dayCreateCnt = new ArrayList<Integer>();
		for(int i=0; i < daysOfMonth; i++) {
			dayUseCnt.add(i, 0);
			dayCreateCnt.add(i, 0);
		}
		
		// 쿠폰 사용량 가져오기
		List<Coupon> useCouponList = cpservice.getUseCouponList(shop, startDate, endDate, grade, code);
		sdf = new SimpleDateFormat("dd");
		for(int j=0; j<useCouponList.size(); j++) {
			Coupon cm = useCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getUsedate()));
			dayUseCnt.set(checkDay-1, dayUseCnt.get(checkDay-1)+1);
			
			}
		// 쿠폰 발행량 가져오기
		List<Coupon> createCouponList = cpservice.getCreateCouponList(shop, startDate, endDate, grade, code);
		for(int j=0; j<createCouponList.size(); j++) {
			Coupon cm = createCouponList.get(j);
			int checkDay = Integer.parseInt(sdf.format(cm.getCreateday()));
			dayCreateCnt.set(checkDay-1, dayCreateCnt.get(checkDay-1)+1);
			}
		
		nextView.addObject("dayUseCnt", dayUseCnt);
		nextView.addObject("dayCreateCnt", dayCreateCnt);
		nextView.addObject("srchday", day);
		nextView.addObject("srchid", shop);
		nextView.addObject("srchcode", code);
		nextView.addObject("srchgrade", grade);
		nextView.addObject("gradeList", gradeList);
		nextView.addObject("couponList", couponList);
		nextView.addObject("storeList", storeList);
		return nextView;
	}

	//슈퍼관리자 쿠폰발행페이지
  	@GetMapping("/super/couponpublish") 
  	public ModelAndView superCouponPublish(){ 
  		ModelAndView nextView = new ModelAndView("super/superManagerCouponPublish");
  		List<CouponCore> allCoupon = cpservice.getCouponList(); // 전체쿠폰리스트
  		List<Grade> gradeList = grservice.findAllAsc();
  		nextView.addObject("allCoupon", allCoupon);
  		nextView.addObject("gradeList", gradeList);
  		
  		return nextView;
  	}
  	
  	//슈퍼관리자 등급관리
  	@GetMapping("/super/gradelist") 
  	private ModelAndView superGradeList() {
  		//슈퍼관리자 사용자별 쿠폰 리스트
  		ModelAndView nextView = new ModelAndView("super/superManagerGradeList");
  		List<Grade> gradeList = grservice.findAll();
  		
  		nextView.addObject("gradeList", gradeList);
  		return nextView;
  	}
  	
  	//슈퍼관리자 카카오발송 리스트
  	@RequestMapping("/super/kakaolist") 
  	public ModelAndView superKakaoList(HttpServletRequest request, @PageableDefault Pageable pageable) throws Exception { 
  		ModelAndView nextView = new ModelAndView("super/superManagerKakaoList");
  		
  		Page<Log> logList = logservice.getLogs(pageable, "KAKAO");
  		Paging paging = new Paging();
  		if(logList != null) {
  			int curPage = pageable.getPageNumber();
  			if(curPage == 0) curPage = curPage + 1;
  			paging.Pagination((int)logList.getTotalElements(), curPage);
  		}
  		nextView.addObject("logList", logList);
  		nextView.addObject("paging", paging);
  	
  		return nextView;
  	}
  	
  	//슈퍼관리자 카카오발송대기 리스트
	@RequestMapping("/super/sendlist") 
	public ModelAndView superSendList(@PageableDefault Pageable pageable) throws Exception { 
		ModelAndView nextView = new ModelAndView("super/superManagerSendList");
		
		Page<Send> sendList = sendservice.findAll(pageable);
		int sendCount = (int) sendList.getTotalElements();
		Paging paging = new Paging();
		if(sendList != null) {
			int curPage = pageable.getPageNumber();
			if(curPage == 0) curPage = curPage + 1;
			paging.Pagination((int)sendList.getTotalElements(), curPage);
		}
		nextView.addObject("sendList", sendList);
		nextView.addObject("totalcount", sendCount);
		nextView.addObject("paging", paging);
	
		return nextView;
	}
  	
  	//카카오FORM페이지 이동
  	@GetMapping("/super/kakaoform")
  	public ModelAndView storeAdd() {
  		ModelAndView nextView = new ModelAndView("super/superManagerKakaoSend");
  		List<Grade> gradeList = grservice.findAllAsc();
  		nextView.addObject("gradeList", gradeList);
  		return nextView;
  	}
  	
  	//카카오 알림톡 발송
  	@RequestMapping(value= "/super/kakaosend", method = RequestMethod.POST) 
  	public ModelAndView kakaoSend(User user, HttpServletRequest request) throws Exception { 
  		ModelAndView nextView = null;
  		HttpSession httpSession = request.getSession(true);
  		Store loginUser = (Store) httpSession.getAttribute("loginUser");
  		Date now = new Date();
  		Calendar cal = Calendar.getInstance();
  		cal.setTime(now);
  		cal.add(Calendar.DAY_OF_MONTH, 7);
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		if(loginUser == null) { //관리자 정보가 없을경우 로그아웃처리
  			nextView = new ModelAndView("logout");
  		}else{
  			nextView = new ModelAndView("super/superManagerKakaoSend");
  			String template = request.getParameter("template");
  			String deletedate = "";
			if(template.equals("10049")) {
				deletedate = sdf.format(cal.getTime());
			}
			
  			if(request.getParameter("type").equals("user")) {
  				//사용자 지정하여 발송할경우
  				User userdata = service.userCheck(user.getUsercode());
  				Send send = new Send();
				send.setCreatedate(now);
				send.setDeletedate(deletedate);
				send.setGrade(userdata.getGrade());
				send.setHomestore(userdata.getHomestore());
				send.setSendtype("KAKAO");
				send.setTemplateid(template);
				send.setUsercode(userdata.getUsercode());
				send.setUsername(userdata.getUsername());
				send.setBirthday(userdata.getBirthday());
				send.setPhone(userdata.getPhone());
				send.setTotalbuy(userdata.getTotalbuy());
				
				sendservice.sendSave(send);
  			}else if(request.getParameter("type").equals("grade")) {
  				//등급 지정하여 발송할 경우
  				String grade = request.getParameter("grade");
  				List<User> userList = new ArrayList<User>();
  				List<Send> sendList = new ArrayList<Send>();
  				//등급별 사용자 리스트 추출
  				if(grade.equals("ALL")) {
  					userList = service.findAll();
  				}else {
  					userList = service.getUserListByGrade(grade);
  				}
  				for(User u : userList) {
  					Send send = new Send();
  					send.setCreatedate(now);
  					send.setDeletedate(deletedate);
  					send.setGrade(u.getGrade());
  					send.setHomestore(u.getHomestore());
  					send.setSendtype("KAKAO");
  					send.setTemplateid(template);
  					send.setUsercode(u.getUsercode());
  					send.setUsername(u.getUsername());
  					send.setBirthday(user.getBirthday());
  					send.setPhone(user.getPhone());
  					send.setTotalbuy(user.getTotalbuy());
  					
  					sendList.add(send);
  				}
  				sendservice.sendSaveAll(sendList);
  			}
  			nextView.addObject("sendyn", "Y");
  			
  		}
  		return nextView;
  	}
}

