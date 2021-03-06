package com.loccitane.coupon.domain;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@org.hibernate.annotations.DynamicUpdate
@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "coupontomember") //대응하는 테이블 설정
public class CouponMember {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "cptmseq") //대응하는 컬럼명 지정
	private int cptmseq;
	
	@Column(name = "usercode") //대응하는 컬럼명 지정
	private String usercode;

	@Column(name = "couponcode")
	private String cpcode;
	
	@Column(name = "couponno")
	private String couponno;
	
	@Column(name = "createdate")
	private Date createdate;
	
	@Column(name = "createuser")
	private String createuser;
	
	@Column(name = "useyn")
	private String useyn;
	
	@Column(name = "startdate")
	private Date startdate;
	
	@Column(name = "enddate")
	private Date enddate;
	
	@Column(name = "usemanager")
	private String usemanager;
	
	@Column(name = "usedate")
	private Date usedate;
	
	@Column(name = "reason")
	private String reason;
	

	public int getCptmseq() {
		return cptmseq;
	}

	public void setCptmseq(int cptmseq) {
		this.cptmseq = cptmseq;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getCpcode() {
		return cpcode;
	}

	public void setCpcode(String cpcode) {
		this.cpcode = cpcode;
	}

	public String getCouponno() {
		return couponno;
	}

	public void setCouponno(String couponno) {
		this.couponno = couponno;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getUsemanager() {
		return usemanager;
	}

	public void setUsemanager(String usemanager) {
		this.usemanager = usemanager;
	}

	public Date getUsedate() {
		return usedate;
	}

	public void setUsedate(Date usedate) {
		this.usedate = usedate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

}
