package com.loccitane.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.loccitane.user.domain.User;

@Component
public class KakaoService {
	
	@SuppressWarnings("unchecked")
	public static JSONObject post(String templateId, User user){
		JSONObject result = new JSONObject();
		try {
			URL url = new URL("https://devtalkapi.lgcns.com/request/kakao.json");
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
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			
			JSONObject formData = new JSONObject();
			formData.put("service", "1810021384");
			formData.put("mobile", user.getPhone());
			formData.put("template", templateId);
			
			String msg = "";
			switch (templateId) {
				case "10027":
					msg = "[록시땅] 마이 프로방스 멤버십 등급 안내\r\n" + 
						  user.getUsername() + " 고객님, 현재 고객님의 등급은 [" + user.getGrade() + "] 입니다. \r\n" + 
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode() + "\r\n" +
						  "\r\n" +
						  "-고객 등급은 분기별로 한 번씩 업데이트 됩니다.\r\n" + 
						  "-문의사항은 가까운 매장 혹은 록시땅 고객센터(02-2054-0500)으로 연락주시기 바랍니다.";
					break;
				case "10028":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
						  user.getUsername() + " 고객님,\r\n" + 
						  "<[" + user.getGrade() + "] 등급업 축하 기프트 쿠폰>이 발급 되었습니다.\r\n" + 
						  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.\r\n" +
						  "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode();
					break;
				case "10030":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, \r\n" + 
					      "<[" + user.getGrade() + "] 등급 첫 구매 감사 쿠폰>이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요." + 
					      "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode();
					break;
				case "10031":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, <[" + user.getGrade() + "] 등급 첫 구매 감사 쿠폰> \r\n" + 
					      "2종이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요." + 
					      "*나의 보유 쿠폰 확인하기: https://www.myprovence.shop/user/"+user.getUsercode();
					break;
				default:
					break;
			}
			formData.put("message", msg);
			
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
		return result;
	}
}
