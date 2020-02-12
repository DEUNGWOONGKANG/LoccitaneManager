package com.loccitane.coupon.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "v_lc_coupon_to_member") //대응하는 테이블 설정
public class Coupon {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "lc_cptm_seq") //대응하는 컬럼명 지정
	private int seq;

	@Column(name = "lc_user_id") //대응하는 컬럼명 지정
	private String userid;

	@Column(name = "lc_cp_is_cd")
	private String cpcode;
	
	@Column(name = "lc_cp_nm")
	private String cpname;
	
	@Column(name = "lc_cp_dck")
	private String dck;
	
	@Column(name = "lc_cp_dc_cnt")
	private int dccnt;
	
	@Column(name = "lc_cp_is_dt")
	private Date issueday;

	@Column(name = "lc_cp_us_yn")
	private String usedyn;
	
	@Column(name = "lc_cp_is_sd")
	private Date startdate;
	
	@Column(name = "lc_cp_is_ed")
	private Date enddate;
	
	@Column(name = "lc_cp_mm")
	private String memo;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
	
}
