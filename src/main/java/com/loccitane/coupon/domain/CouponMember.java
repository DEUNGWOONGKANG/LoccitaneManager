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
@Table(name = "lc_coupon_to_member") //대응하는 테이블 설정
public class CouponMember {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "lc_cptm_seq") //대응하는 컬럼명 지정
	private int seq;
	
	@Column(name = "lc_user_id") //대응하는 컬럼명 지정
	private String userid;

	@Column(name = "lc_cp_is_cd")
	private String cpcode;
	
	@Column(name = "lc_cp_is_dt")
	private Date issueday;

	@Column(name = "lc_cp_us_yn")
	private String usedyn;
	
	@Column(name = "lc_cp_is_sd")
	private Date startdate;
	
	@Column(name = "lc_cp_is_ed")
	private Date enddate;
	
	@Column(name = "lc_cp_us_nm")
	private String useuserid;
	
	@Column(name = "lc_cp_us_dt")
	private Date usedate;

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

	public Date getUsedate() {
		return usedate;
	}

	public void setUsedate(Date usedate) {
		this.usedate = usedate;
	}

	public String getUseuserid() {
		return useuserid;
	}

	public void setUseuserid(String useuserid) {
		this.useuserid = useuserid;
	}
	
	
}
