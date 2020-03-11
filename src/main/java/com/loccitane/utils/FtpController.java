package com.loccitane.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Controller
public class FtpController {
  
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
   @RequestMapping(value = "/csvupload", method=RequestMethod.GET)
   public void connectFtp() {
     
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
         
         String filePath = "C:/csv/user.csv";     // 나중에 DB에서 필요한 경로 당기면 good
         
         File file= new File(filePath);       // file 객체 생성 (파일 경로 입력)
            fi = new FileInputStream(file);
            chSftp.put(fi, "/csv/user.csv");   // 서버에 파일 보내기
            chSftp.quit();                     // Sftp 연결 종료
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

