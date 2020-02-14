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
	private int cptmseq;
	
	@Column(name = "lc_user_id") //대응하는 컬럼명 지정
	private String cptmuserid;

	@Column(name = "lc_cp_is_cd")
	private String cptmcpcode;
	
	@Column(name = "lc_cp_is_dt")
	private Date cptmissueday;
	
	@Column(name = "lc_cp_is_ps")
	private String cptmgiveuser;
	
	@Column(name = "lc_cp_us_yn")
	private String cptmusedyn;
	
	@Column(name = "lc_cp_is_sd")
	private Date cptmstartdate;
	
	@Column(name = "lc_cp_is_ed")
	private Date cptmenddate;
	
	@Column(name = "lc_cp_us_ps")
	private String cptmuseuserid;
	
	@Column(name = "lc_cp_us_dt")
	private Date cptmusedate;

	public int getCptmseq() {
		return cptmseq;
	}

	public void setCptmseq(int cptmseq) {
		this.cptmseq = cptmseq;
	}

	public String getCptmuserid() {
		return cptmuserid;
	}

	public void setCptmuserid(String cptmuserid) {
		this.cptmuserid = cptmuserid;
	}

	public String getCptmcpcode() {
		return cptmcpcode;
	}

	public void setCptmcpcode(String cptmcpcode) {
		this.cptmcpcode = cptmcpcode;
	}

	public Date getCptmissueday() {
		return cptmissueday;
	}

	public void setCptmissueday(Date cptmissueday) {
		this.cptmissueday = cptmissueday;
	}

	public String getCptmgiveuser() {
		return cptmgiveuser;
	}

	public void setCptmgiveuser(String cptmgiveuser) {
		this.cptmgiveuser = cptmgiveuser;
	}

	public String getCptmusedyn() {
		return cptmusedyn;
	}

	public void setCptmusedyn(String cptmusedyn) {
		this.cptmusedyn = cptmusedyn;
	}

	public Date getCptmstartdate() {
		return cptmstartdate;
	}

	public void setCptmstartdate(Date cptmstartdate) {
		this.cptmstartdate = cptmstartdate;
	}

	public Date getCptmenddate() {
		return cptmenddate;
	}

	public void setCptmenddate(Date cptmenddate) {
		this.cptmenddate = cptmenddate;
	}

	public String getCptmuseuserid() {
		return cptmuseuserid;
	}

	public void setCptmuseuserid(String cptmuseuserid) {
		this.cptmuseuserid = cptmuseuserid;
	}

	public Date getCptmusedate() {
		return cptmusedate;
	}

	public void setCptmusedate(Date cptmusedate) {
		this.cptmusedate = cptmusedate;
	}

	
}
