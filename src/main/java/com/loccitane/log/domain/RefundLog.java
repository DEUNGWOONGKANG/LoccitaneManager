package com.loccitane.log.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "refundlog") //대응하는 테이블 설정
public class RefundLog { //반품
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq") 
	private int seq;
	
	@Column(name = "usercode") 
	private String usercode;
	
	@Column(name = "username") 
	private String username;
	
	@Column(name = "pasttotalbuy")
	private BigDecimal pasttotalbuy;
	
	@Column(name = "nowtotalbuy")
	private BigDecimal nowtotalbuy;
	
	@Column(name = "pastupdate")
	private Date pastupdate;
	
	@Column(name = "lastupdate")
	private Date lastupdate;

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

	public BigDecimal getPasttotalbuy() {
		return pasttotalbuy;
	}

	public void setPasttotalbuy(BigDecimal pasttotalbuy) {
		this.pasttotalbuy = pasttotalbuy;
	}

	public BigDecimal getNowtotalbuy() {
		return nowtotalbuy;
	}

	public void setNowtotalbuy(BigDecimal nowtotalbuy) {
		this.nowtotalbuy = nowtotalbuy;
	}

	public Date getPastupdate() {
		return pastupdate;
	}

	public void setPastupdate(Date pastupdate) {
		this.pastupdate = pastupdate;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
