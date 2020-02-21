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
	
	@Column(name = "userid") //대응하는 컬럼명 지정
	private String userid;

	@Column(name = "couponcode")
	private String cpcode;
	
	@Column(name = "couponname")
	private String cpname;
	
	@Column(name = "discountkind")
	private String dck;
	
	@Column(name = "discountvalue")
	private int dccnt;
	
	@Column(name = "createdate")
	private Date issueday;

	@Column(name = "useyn")
	private String usedyn;
	
	@Column(name = "startdate")
	private Date startdate;
	
	@Column(name = "enddate")
	private Date enddate;
	
	@Column(name = "couponinfo")
	private String memo;
	
	@Column(name = "useminimum")
	private int minimum;
	
	@Column(name = "discountmax")
	private int discountmax;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public int getDccnt() {
		return dccnt;
	}

	public void setDccnt(int dccnt) {
		this.dccnt = dccnt;
	}

	public Date getIssueday() {
		return issueday;
	}

	public void setIssueday(Date issueday) {
		this.issueday = issueday;
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

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getDiscountmax() {
		return discountmax;
	}

	public void setDiscountmax(int discountmax) {
		this.discountmax = discountmax;
	}

}
