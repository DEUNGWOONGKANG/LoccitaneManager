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
@Table(name = "coupon") //대응하는 테이블 설정
public class CouponCore {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "couponseq") //대응하는 컬럼명 지정
	private int seq;

	@Column(name = "couponcode")
	private String cpcode;
	
	@Column(name = "couponname")
	private String cpname;
	
	@Column(name = "discountkind")
	private String dck;
	
	@Column(name = "discountvalue")
	private String dccnt;
	
	@Column(name = "useminimum")
	private String minimum;
	
	@Column(name = "discountmax")
	private String dcmax;

	@Column(name = "useyn")
	private String useyn;
	
	@Column(name = "couponinfo")
	private String memo;
	
	@Column(name = "createuser")
	private String createuser;

	@Column(name = "createdate")
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

	public String getDccnt() {
		return dccnt;
	}

	public void setDccnt(String dccnt) {
		this.dccnt = dccnt;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getDcmax() {
		return dcmax;
	}

	public void setDcmax(String dcmax) {
		this.dcmax = dcmax;
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
