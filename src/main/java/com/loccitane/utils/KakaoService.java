package com.loccitane.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class KakaoService {
	
	public static void post(String strUrl, String jsonMessage){
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
			wr.write(jsonMessage); //json 형식의 message 전달 
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
