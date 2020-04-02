package com.loccitane.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.coupon.service.CouponService;
import com.loccitane.user.domain.User;
import com.loccitane.user.service.UserService;
@Service // 서비스 클래스임을 나타냄
public class ExcelDownService {
	@Autowired
	UserService service;
	
	@Autowired
	CouponService cpservice;
	
	public void excelDown(String type) throws IOException {
		FileOutputStream fileout = null;
		Workbook wb = new XSSFWorkbook();
		List<User> userList = new ArrayList<User>();
		List<CouponCore> couponList = new ArrayList<CouponCore>();
		List<CouponMember> couponMemList = new ArrayList<CouponMember>();
		try {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DATE, -1);
			
			if(type.equals("user")) {
				userList = service.getUpdateUserList(now, cal.getTime());
			}else if(type.equals("coupon")) {
				couponList = cpservice.getUpdateCoupon(now, cal.getTime());
			}else if(type.equals("coupontomember")) {
				couponMemList = cpservice.getUpdateCoupontomember(now, cal.getTime());
			}
	
		    // 워크북 생성
		    Sheet sheet = wb.createSheet(type);
		    Row row = null;
		    Cell cell = null;
		    int rowNo = 0;
	
		    // 테이블 헤더용 스타일
		    CellStyle headStyle = wb.createCellStyle();
	
		    // 가는 경계선을 가집니다.
		    headStyle.setBorderTop(BorderStyle.THIN);
		    headStyle.setBorderBottom(BorderStyle.THIN);
		    headStyle.setBorderLeft(BorderStyle.THIN);
		    headStyle.setBorderRight(BorderStyle.THIN);
	
		    // 배경색은 노란색입니다.
		    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
		    // 데이터는 가운데 정렬합니다.
		    headStyle.setAlignment(HorizontalAlignment.CENTER);
		    CreationHelper createHelper = wb.getCreationHelper();
		    // 데이터용 경계 스타일 테두리만 지정
		    CellStyle bodyStyle = wb.createCellStyle();
		    bodyStyle.setBorderTop(BorderStyle.THIN);
		    bodyStyle.setBorderBottom(BorderStyle.THIN);
		    bodyStyle.setBorderLeft(BorderStyle.THIN);
		    bodyStyle.setBorderRight(BorderStyle.THIN);
		    
		    CellStyle dateStyle = wb.createCellStyle();
		    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		    
		    List<String> title = new ArrayList<String>();
		    
		    String[] usertitle = {"NO", "USERCODE", "PHONE"};
		    String[] cptitle = {"COUPONSEQ", "COUPONCODE", "COUPONNAME", "DISCOUNTKIND", "DISCOUNTVALUE", "USEMINIMUM", "DISCOUNTMAX", "USEYN", "COUPONINFO", "CREATEUSER", "CREATEDATE"};
		    String[] cptmtitle = {"CPTMSEQ", "USERCODE", "COUPONCODE", "COUPONNO", "CREATEDATE", "CREATEUSER", "REASON", "STARTDATE", "ENDDATE", "USEYN", "USEMANAGER", "USEDATE"};
		    
		    if(type.equals("user")) {
		    	for(String i : usertitle) {
		    		title.add(i);
		    	}
			}else if(type.equals("coupon")) {
				for(String i : cptitle) {
		    		title.add(i);
		    	}
			}else if(type.equals("coupontomember")) {
				for(String i : cptmtitle) {
		    		title.add(i);
		    	}
			}
		    // 헤더 생성
		    row = sheet.createRow(rowNo++);
		    for(int t=0; t<title.size(); t++) {
			    cell = row.createCell(t);
			    cell.setCellStyle(headStyle);
			    cell.setCellValue(title.get(t));
		    }
	
		    // 데이터 부분 생성
		    if(type.equals("user")) {
		    	for(int i=0; i<userList.size(); i++) {
			    	User user = userList.get(i);
			        row = sheet.createRow(rowNo++);
			        cell = row.createCell(0);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(i+1);
			        cell = row.createCell(1);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(user.getUsercode());
			        cell = row.createCell(2);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(user.getPhone().substring(user.getPhone().length()-4, user.getPhone().length()));
			    }
			}else if(type.equals("coupon")) {
				for(int i=0; i<couponList.size(); i++) {
			    	CouponCore data = couponList.get(i);
			        row = sheet.createRow(rowNo++);
			        cell = row.createCell(0);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getSeq());
			        cell = row.createCell(1);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCpcode());
			        cell = row.createCell(2);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCpname());
			        cell = row.createCell(3);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getDck());
			        cell = row.createCell(4);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getDccnt());
			        cell = row.createCell(5);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getMinimum());
			        cell = row.createCell(6);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getDcmax());
			        cell = row.createCell(7);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getUseyn());
			        cell = row.createCell(8);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getMemo());
			        cell = row.createCell(9);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCreateuser());
			        cell = row.createCell(10);
			        cell.setCellStyle(dateStyle);
			        cell.setCellValue(data.getCreatedate());
			    }
			}else if(type.equals("coupontomember")) {
				for(int i=0; i<couponMemList.size(); i++) {
			    	CouponMember data = couponMemList.get(i);
			        row = sheet.createRow(rowNo++);
			        cell = row.createCell(0);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getSeq());
			        cell = row.createCell(1);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getUsercode());
			        cell = row.createCell(2);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCpcode());
			        cell = row.createCell(3);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCouponno());
			        cell = row.createCell(4);
			        cell.setCellStyle(dateStyle);
			        cell.setCellValue(data.getCreatedate());
			        cell = row.createCell(5);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getCreateuser());
			        cell = row.createCell(6);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getReason());
			        cell = row.createCell(7);
			        cell.setCellStyle(dateStyle);
			        cell.setCellValue(data.getStartdate());
			        cell = row.createCell(8);
			        cell.setCellStyle(dateStyle);
			        cell.setCellValue(data.getEnddate());
			        cell = row.createCell(9);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getUseyn());
			        cell = row.createCell(10);
			        cell.setCellStyle(bodyStyle);
			        cell.setCellValue(data.getUsemanager());
			        cell = row.createCell(11);
			        cell.setCellStyle(dateStyle);
			        cell.setCellValue(data.getUsedate());
			    }
			}
		    String path = "C:/exceldata/";
		    //String fileName = sdf.format(now) + type;
		    String fileName = type;
		    // 엑셀 출력
	    	File file = new File(path+fileName+".xlsx");
	    	fileout = new FileOutputStream(file);
			wb.write(fileout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fileout.close();
			wb.close();
		}
	}
}
