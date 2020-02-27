package com.loccitane.log.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "adminlog") //대응하는 테이블 설정
public class Log {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq") 
	private int seq;
	
	@Column(name = "userid") 
	private String userid;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "logkind")
	private String logkind;
	
	@Column(name = "logcontent")
	private String logcontent;
	
	@Column(name = "logdate")
	private Date logdate;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogkind() {
		return logkind;
	}

	public void setLogkind(String logkind) {
		this.logkind = logkind;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	public Date getLogdate() {
		return logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}
	
}
