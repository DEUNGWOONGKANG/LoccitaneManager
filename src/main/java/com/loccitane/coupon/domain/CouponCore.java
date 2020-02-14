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
@Table(name = "lc_coupon_core") //대응하는 테이블 설정
public class CouponCore {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "lc_cp_seq") //대응하는 컬럼명 지정
	private int seq;

	@Column(name = "lc_cp_cd")
	private String cpcode;
	
	@Column(name = "lc_cp_nm")
	private String cpname;
	
	@Column(name = "lc_cp_dck")
	private String dck;
	
	@Column(name = "lc_cp_dc_cnt")
	private int dccnt;

	@Column(name = "lc_cp_trg_lm")
	private String target;
	
	@Column(name = "lc_cp_us_yn")
	private String useyn;
	
	@Column(name = "lc_cp_mm")
	private String memo;
	
	@Column(name = "lc_cp_cr_if")
	private String createuser;

	@Column(name = "lc_cp_cr_dt")
	private Date createdate;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
