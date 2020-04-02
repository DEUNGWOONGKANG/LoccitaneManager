package com.loccitane.utils;

import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.loccitane.coupon.domain.CouponCore;
import com.loccitane.coupon.domain.CouponMember;
import com.loccitane.user.domain.User;

@Service
public class ApiService {
	public void userAddCall(User user) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    String phone = "";
	    if(user.getPhone().length() > 3) {
	    	phone = user.getPhone().substring(user.getPhone().length()-4, user.getPhone().length());
	    }else{
	    	phone = user.getPhone();
	    }
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		.path("/api/user/add")
	    		.queryParam("usercode", user.getUsercode())
	    		.queryParam("phone", phone)
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    
	    System.out.println(responseStr);
	    
	}
	
	public void userModifyCall(User user) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    String phone = "";
	    if(user.getPhone().length() > 3) {
	    	phone = user.getPhone().substring(user.getPhone().length()-4, user.getPhone().length());
	    }else{
	    	phone = user.getPhone();
	    }
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		.path("/api/user/modify")
	    		.queryParam("usercode", user.getUsercode())
	    		.queryParam("phone", phone)
	    		.queryParam("status", user.getStatus())
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    System.out.println(responseStr);
	    
	}
	
	public void couponAddCall(CouponCore coupon) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		.path("/api/coupon/add")
	    		.queryParam("couponcode", coupon.getCpcode())
	    		.queryParam("couponname", coupon.getCpname())
	    		.queryParam("useminimum", coupon.getMinimum())
	    		.queryParam("discountkind", coupon.getDck())
	    		.queryParam("discountvalue", coupon.getDccnt())
	    		.queryParam("discountmax", coupon.getDcmax())
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    
	    System.out.println(responseStr);
	    
	}
	
	public void couponModifyCall(CouponCore coupon) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		.path("/api/coupon/modify")
	    		.queryParam("couponcode", coupon.getCpcode())
	    		.queryParam("couponname", coupon.getCpname())
	    		.queryParam("useminimum", coupon.getMinimum())
	    		.queryParam("discountkind", coupon.getDck())
	    		.queryParam("discountvalue", coupon.getDccnt())
	    		.queryParam("discountmax", coupon.getDcmax())
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    System.out.println(responseStr);
	    
	}
	
	public void coupontomemberAddCall(CouponMember coupon) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		//.host("localhost")
	    		//.port("8082")
	    		.path("/api/coupontomember/add")
	    		.queryParam("usercode", coupon.getUsercode())
	    		.queryParam("couponcode", coupon.getCpcode())
	    		.queryParam("couponno", coupon.getCouponno())
	    		.queryParam("useyn", coupon.getUseyn())
	    		.queryParam("usemanager", coupon.getUsemanager())
	    		.queryParam("usedate", coupon.getUsedate())
	    		.queryParam("startdate", sdf.format(coupon.getStartdate()))
	    		.queryParam("enddate", sdf.format(coupon.getEnddate()))
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    System.out.println(responseStr);
	    
	}
	
	public void coupontomemberModifyCall(CouponMember coupon) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    URI uri = UriComponentsBuilder.newInstance()
	    		.scheme("https")
	    		.host("myprovence.shop")
	    		//.host("localhost")
	    		//.port("8082")
	    		.path("/api/coupontomember/modify")
	    		.queryParam("couponno", coupon.getCouponno())
	    		.queryParam("useyn", coupon.getUseyn())
	    		.queryParam("usemanager", coupon.getUsemanager())
	    		.queryParam("usedate", sdf.format(coupon.getUsedate()))
	    		.build()
	    		.encode()
	    		.toUri();
	    
	    String responseStr = restTemplate.getForObject(uri, String.class);
	    System.out.println(responseStr);
	    
	}
}
