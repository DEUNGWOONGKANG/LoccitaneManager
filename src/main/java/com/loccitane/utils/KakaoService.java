package com.loccitane.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.loccitane.store.domain.Store;
import com.loccitane.user.domain.User;

@Component
public class KakaoService {
	
	public static void post(String templateId, User user){
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
						  "\r\n" + 
						  "*고객 등급은 분기별로 한 번씩 업데이트 됩니다.\r\n" + 
						  "*문의사항은 가까운 매장 혹은 록시땅 고객센터(02-2054-0500)으로 연락주시기 바랍니다.";
					break;
				case "10028":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
						  user.getUsername() + " 고객님, \r\n" + 
						  "<[" + user.getGrade() + "] 등급업 축하 기프트 쿠폰>이 발급 되었습니다.\r\n" + 
						  "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.";
					break;
				case "10030":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, \r\n" + 
					      "<[" + user.getGrade() + "] 등급 첫 구매 감사 쿠폰>이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.";
					break;
				case "10031":
					msg = "[록시땅] 쿠폰 발급 안내\r\n" + 
					      user.getUsername() + " 고객님, <[" + user.getGrade() + "] 등급 첫 구매 감사 쿠폰> \r\n" + 
					      "2종이 발급 되었습니다.\r\n" + 
					      "지금 바로 사용 가능한 나의 쿠폰을 확인해보세요.";
					break;
				default:
					break;
			}
			formData.put("message", msg);
			
			System.out.println(formData.toJSONString());
			wr.write(formData.toJSONString()); //json 형식의 message 전달 
			wr.flush();

			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Stream을 처리해줘야 하는 귀찮음이 있음.
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e){
			System.err.println(e.toString());
		}
	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		// TODO Auto-generated method stub
//		User user = new User();
//		user.setPhone("01076151510");
//		user.setUsername("황인재");
//		user.setGrade("프리스티지");
//		post("10031", user);
//	}


//	String myResult = "";
//
//    try {
//        //   URL 설정하고 접속하기 
//        URL url = new URL("https://devtalkapi.lgcns.com/request/kakao.json"); // URL 설정 
//
//        HttpsURLConnection http = (HttpsURLConnection) url.openConnection(); // 접속 
//        //-------------------------- 
//        //   전송 모드 설정 - 기본적인 설정 
//        //-------------------------- 
//        http.setDefaultUseCaches(false);
//        http.setDoInput(true); // 서버에서 읽기 모드 지정 
//        http.setDoOutput(true); // 서버로 쓰기 모드 지정  
//        http.setRequestMethod("POST"); // 전송 방식은 POST
//
//
//
//        //--------------------------
//        // 헤더 세팅
//        //--------------------------
//        // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다 
//        http.setRequestProperty("Content-type", "application/json");
//
//
//        //-------------------------- 
//        //   서버로 값 전송 
//        //-------------------------- 
//        StringBuffer buffer = new StringBuffer();
//
//        //HashMap으로 전달받은 파라미터가 null이 아닌경우 버퍼에 넣어준다
//        if (pList != null) {
//
//            Set key = pList.keySet();
//
//            for (Iterator iterator = key.iterator(); iterator.hasNext();) {
//                String keyName = (String) iterator.next();
//                String valueName = pList.get(keyName);
//                buffer.append(keyName).append("=").append(valueName);
//            }
//        }
//
//        OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
//        PrintWriter writer = new PrintWriter(outStream);
//        writer.write(buffer.toString());
//        writer.flush();
//
//
//        //--------------------------
//        //   Response Code
//        //--------------------------
//        //http.getResponseCode();
//
//
//        //-------------------------- 
//        //   서버에서 전송받기 
//        //-------------------------- 
//        InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
//        BufferedReader reader = new BufferedReader(tmp);
//        StringBuilder builder = new StringBuilder();
//        String str;
//        while ((str = reader.readLine()) != null) {
//            builder.append(str + "\n");
//        }
//        myResult = builder.toString();
//        return myResult;
//
//    } catch (MalformedURLException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    return myResult;
//}

}
