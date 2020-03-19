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
@Table(name = "v_coupontomember") //대응하는 테이블 설정
public class Coupon {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "cptmseq") //대응하는 컬럼명 지정
	private int seq;
	
	@Column(name = "usercode") //대응하는 컬럼명 지정
	private String usercode;

	@Column(name = "username")
	private String username;
	
	@Column(name = "couponcode")
	private String cpcode;
	
	@Column(name = "couponno")
	private String couponno;
	
	@Column(name = "couponname")
	private String cpname;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "discountkind")
	private String dck;
	
	@Column(name = "discountvalue")
	private String dccnt;
	
	@Column(name = "createdate")
	private Date createdate;
	
	@Column(name = "createuser")
	private String createuser;

	@Column(name = "startdate")
	private Date startdate;
	
	@Column(name = "enddate")
	private Date enddate;
	
	@Column(name = "useyn")
	private String usedyn;
	
	@Column(name = "usemanager")
	private String usemanager;
	
	@Column(name = "usedate")
	private Date usedate;
	
	@Column(name = "couponinfo")
	private String memo;
	
	@Column(name = "useminimum")
	private String minimum;
	
	@Column(name = "discountmax")
	private String discountmax;
	
	@Column(name = "reason")
	private String reason;

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getCpname() {
		return cpname;
	}

	public void setCpname(String cpname) {
		this.cpname = cpname;
	}

	public String getDck() {
		return dck;
	}

	public void setDck(String dck) {
		this.dck = dck;
	}

	public String getDccnt() {
		return dccnt;
	}

	public void setDccnt(String dccnt) {
		this.dccnt = dccnt;
	}

	public Date getCreateday() {
		return createdate;
	}

	public void setCreateday(Date createdate) {
		this.createdate = createdate;
	}

	public String getUsedyn() {
		return usedyn;
	}

	public void setUsedyn(String usedyn) {
		this.usedyn = usedyn;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getDiscountmax() {
		return discountmax;
	}

	public void setDiscountmax(String discountmax) {
		this.discountmax = discountmax;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCouponno() {
		return couponno;
	}

	public void setCouponno(String couponno) {
		this.couponno = couponno;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
