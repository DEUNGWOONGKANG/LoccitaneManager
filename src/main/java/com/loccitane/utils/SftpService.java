package com.loccitane.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Service
public class SftpService {
  
   protected static String FTP_IP   = "203.245.44.51"; // FTP 접속지 IP
   protected static int    FTP_PORT = 22;             // FTP 접속지 Port
   protected static String FTP_ID   = "imrnortaty";        // ID
   protected static String FTP_PWD  = "Conc3n200!";        // PASSWORD
   protected static String FTP_PATH = "";
   ChannelSftp chSftp               = null;
   FileInputStream fi               = null;
     
   /**
    * FTP 방식으로 연결
    */
   public void sendFile() {
     
      // FTP 관련 객체 선언
      Session ses = null;             // 접속계정
      Channel ch  = null;             // 접속
      JSch jsch   = new JSch();       // jsch 객체를 생성
     
      try {
         // 세션 객체를 생성(사용자 이름, 접속할 호스트, 포트)
         ses = jsch.getSession(FTP_ID, FTP_IP, FTP_PORT);
         // 비밀번호 설정
         ses.setPassword(FTP_PWD);
         
         // 세션과 관련된 정보를 설정
         Properties p = new Properties();
        
         // 호스트 정보를 검사하지 않음
         p.put("StrictHostKeyChecking", "no");
         ses.setConfig(p);
        
         System.out.println("연결중");
         
         // 접속
         ses.connect();        
 
         // 채널을 오픈(sftp)
         ch = ses.openChannel("sftp");
         
         // 채널에 연결(sftp)        
         ch.connect();        
           
         // 채널을 FTP용 채널 객체로 개스팅
         chSftp = (ChannelSftp)ch;     
         
         Date now = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
         String today = sdf.format(now);
         
         String filePath = "C:/exceldata/";
         
         String userExcel = today+"user.xlsx";
         String userFilePath = filePath+userExcel;
         String couponExcel = today+"coupon.xlsx";
         String couponFilePath = filePath+couponExcel;
         String coupontomemberExcel = today+"coupontomember.xlsx";
         String coupontomemberFilePath = filePath+coupontomemberExcel;
         
         chSftp.cd("/imrnortaty/exceldata");  
         
         File userFile= new File(userFilePath);  
         fi = new FileInputStream(userFile);
         chSftp.put(fi, userExcel);   // 서버에 파일 보내기
         
         File couponFile= new File(couponFilePath);  
         fi = new FileInputStream(couponFile);
         chSftp.put(fi, couponExcel);   // 서버에 파일 보내기
         
         File coupontomemberFile= new File(coupontomemberFilePath);  
         fi = new FileInputStream(coupontomemberFile);
         chSftp.put(fi, coupontomemberExcel);   // 서버에 파일 보내기
         
         chSftp.quit();                // Sftp 연결 종료
         System.out.println("FTP 연결을 종료합니다.");
            
            
         } catch(SftpException e) { 
            e.printStackTrace();
         } catch(FileNotFoundException e) {
            e.printStackTrace();
         } catch(Exception e) { 
             System.err.println(e.getMessage());
         } finally {
            try {
               fi.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         
   }
  
}

