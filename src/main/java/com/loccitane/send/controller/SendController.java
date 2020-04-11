package com.loccitane.send.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.log.domain.Log;
import com.loccitane.log.service.LogService;
import com.loccitane.send.domain.Send;
import com.loccitane.send.service.SendService;
import com.loccitane.store.domain.Store;
import com.loccitane.store.service.StoreService;
import com.loccitane.user.domain.User;

@Controller
public class SendController {
	static Logger logger = LoggerFactory.getLogger(SendController.class);
	@Autowired
	SendService service;
	@Autowired
	StoreService storeService;
	@Autowired
	LogService logService;
	
	//카카오, LMS 발송
  	@GetMapping("/super/send")
  	public ModelAndView send() {
  		ModelAndView nextView = new ModelAndView("super/superManagerSendList");
  		List<Send> sendList = service.getAll();
  		int kakao = 0;
  		int lms = 0;
  		for(Send send : sendList) {
  			//홈스토어 정보 가져오기
  			Store homestore = storeService.getHomestore(send.getHomestore()); 
  			User user = new User();
  			user.setGrade(send.getGrade());
  			user.setPhone(send.getPhone());
  			user.setUsercode(send.getUsercode());
  			user.setUsername(send.getUsername());
  			
  			if(send.getSendtype().equals("KAKAO")) {//타입이 KAKAO인경우
  				JSONObject result = kakaoSend(send.getTemplateid(), user, send.getDeletedate(), homestore);
  				String status = (String) result.get("status");
  				if(status != null && status.equals("OK")) {
  					kakao ++;
  				}
  			}else if(send.getSendtype().equals("LMS")) { //타입이 LMS인경우
  				JSONObject result = lmsSend(user, homestore);
  				String status = (String) result.get("status");
  				if(status != null && status.equals("MMS_0000")) {
  					lms ++;
  				}
  			}
  		}
  		Date now = new Date();
  		Log log = new Log();
  		log.setUserid("system");
		log.setUsername("system");
		log.setLogkind("KAKAO");
		log.setLogcontent("카카오 : " + kakao + "명 | LMS : " + lms + "명 발송완료");
		log.setLogdate(now);
		logService.saveLog(log);
		
		//모든 발송대기 데이터 삭제
		service.sendDelete();
  		
		nextView.addObject("sendyn", "Y");
  		return nextView;
  	}
  	
  	@SuppressWarnings("unchecked")
	public JSONObject kakaoSend(String templateId, User user, String alarmDate, Store homestore){
		JSONObject result = new JSONObject();
		try {
			String store = "대표번호";
			String tel = "02-2054-0500";
			if(homestore != null) {
				store = homestore.getName();
				tel = homestore.getTel();
			}
			URL url = new URL("https://talkapi.lgcns.com/request/kakao.json");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("authToken", "fqLDeomWcb7MojWpPoCeyw==");
			con.setRequestProperty("serverName", "LOKR00");
			con.setDoInput(true);
			con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			
			JSONObject formData = new JSONObject();
			formData.put("service", "1810021384");
			formData.put("mobile", user.getPhone());
			formData.put("template", templateId);
			String grade = "";
			if(user.getGrade().equals("REGULAR")) {
				grade = "레귤러";
			}else if(user.getGrade().equals("PREMIUM")) {
				grade = "프리미엄";
			}else if(user.getGrade().equals("LOYAL")) {
				grade = "로열";
			}else if(user.getGrade().equals("PRESTIGE")) {
				grade = "프레스티지";
			}
			String msg = "";
			switch (templateId) {
				case "10027":
					msg = "[록시땅] 마이 프로방스 멤버십 등급 안내\r\n" + 
						  user.getUsername() + " 고객님, 현재 고객님의 등급은 [" + grade + "] 입니다. \r\n" + 
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + "\r\n" +
						  "*고객 등급은 분기별로 한 번씩 업데이트 됩니다.\r\n" + 
						  "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10028":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
						  user.getUsername() + " 고객님,\r\n" + 
						  "<[" + grade + "] 등급업 축하 기프트 쿠폰>이 발급 되었습니다.\r\n" + 
						  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" +
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode()+
						  "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10030":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, \r\n" + 
					      "<[" + grade + "] 등급 첫 구매 감사 쿠폰>이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" + 
					      "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + 
					      "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10031":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, <[" + grade + "] 등급 첫 구매 감사 쿠폰> \r\n" + 
					      "2종이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" + 
					      "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + 
					      "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10049": 
					msg = "[록시땅] 쿠폰 소멸 예정 안내\r\n" +  //소멸예정 쿠폰 리스트
					      user.getUsername() + " 고객님, 보유하고 계신 쿠폰이 7일 후 소멸될 예정입니다.("+alarmDate+"까지 사용 가능)\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n"  +
					      "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + 
					      "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10050":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
						  user.getUsername() + " 고객님,\r\n" + 
						  "<프레스티지 스페셜 축하 기프트 쿠폰>이 발급 되었습니다.\r\n" + 
						  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" +
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() +
						  "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10051":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
						  user.getUsername() + " 고객님, <[프레스티지] 등급 신제품 선공개 쿠폰>이 발급 되었습니다.\r\n" + 
						  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" +
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() +
						  "\r\n" +
						  "문의)\r\n" +
						  "- 나의 이용 매장: " + store + " / " + tel + "\r\n" +
						  "- 록시땅 고객 센터: 02-2054-0500";
					break;
				case "10052":
					msg = "[록시땅] 마이 프로방스 멤버십 혜택 안내\r\n" + 
						  user.getUsername() + " 고객님, 록시땅 마이 프로방스 멤버십의 회원이 되신 것을 축하드립니다.\r\n" +
						  "현재 고객님의 등급은 ["+grade+"]입니다.\r\n" +
						  "지금 바로 다양한 멤버십 혜택을 확인해보세요.\r\n" +
						  "*멤버십 혜택 보기:http://mini.loccitane.co.kr/newsite/event/mdm/2003/index.html";
					break;
				default:
					break;
			}
			formData.put("message", msg);
			//System.out.println(msg);
			wr.write(formData.toJSONString()); //json 형식의 message 전달 
			wr.flush();
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
				JSONParser parser = new JSONParser();
				result = (JSONObject) parser.parse(sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e){
			System.err.println(e.toString());
		}
		String status = (String) result.get("status");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(status != null) {
			if(status.equals("OK")) {
				logger.info(sdf.format(now)+" [KAKAO]발송 성공:::"+ user.getUsername() +"["+user.getPhone() + "]  STATUS::: " + status + "|| TEMPLATE:::"+templateId);
			}else {
				logger.info(sdf.format(now)+" [KAKAO]발송  실패:::"+ user.getUsername() +"["+user.getPhone() + "]  STATUS::: " + status + "|| TEMPLATE:::"+templateId);
			}
		}else {
			logger.info(sdf.format(now)+" [KAKAO]발송 실패:::" + user.getPhone() + " || STATUS::: NULL || TEMPLATE:::"+templateId);
		}
		return result;
	}
  	
  	@SuppressWarnings("unchecked")
	public static JSONObject lmsSend(User user, Store homestore){
		JSONObject result = new JSONObject();
		try {
			String store = "대표번호";
			String tel = "02-2054-0500";
			if(homestore != null) {
				store = homestore.getName();
				tel = homestore.getTel();
			}
			
			URL url = new URL("https://talkapi.lgcns.com/request/lms.json");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("authToken", "fqLDeomWcb7MojWpPoCeyw==");
			con.setRequestProperty("serverName", "LOKR00");
			con.setDoInput(true);
			con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정 
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			
			JSONObject formData = new JSONObject();
			formData.put("service", "2030041914");
			formData.put("callbackNo", "0220540500");
			formData.put("mobile", user.getPhone());
			
			String msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
				  user.getUsername() + " 고객님, 생일 축하드립니다. \r\n" +
				  "[Happy Birthday 10% 할인 쿠폰]이 발급 되었습니다. \r\n" +
				  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" +
				  "▶나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + "\r\n" +
				  "\r\n" +
				  "문의) \r\n" +
				  "- 나의 이용 매장: " + store + " / " + tel;
			formData.put("message", msg);
			//System.out.println(msg);
			wr.write(formData.toJSONString()); //json 형식의 message 전달 
			wr.flush();
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
				JSONParser parser = new JSONParser();
				result = (JSONObject) parser.parse(sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e){
			System.err.println(e.toString());
		}
		String status = (String) result.get("status");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(status != null) {
			if(status.equals("MMS_0000")) {
				logger.info(sdf.format(now)+" [LMS]발송 성공:::"+ user.getUsername() +"["+user.getPhone() + "]  STATUS::: " + status);
			}else {
				logger.info(sdf.format(now)+" [LMS]발송  실패:::"+ user.getUsername() +"["+user.getPhone() + "]  STATUS::: " + status);
			}
		}else {
			logger.info(sdf.format(now)+" [LMS]발송 실패:::" + user.getPhone() + " || STATUS::: NULL");
		}
		return result;
	}
	
}