package com.loccitane.send.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "senddata") //대응하는 테이블 설정
public class Send {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq") 
	private int seq;
	
	@Column(name = "usercode") 
	private String usercode;
	
	@Column(name = "username") 
	private String username;
	
	@Column(name = "grade") 
	private String grade;
	
	@Column(name = "sendtype") 
	private String sendtype;
	
	@Column(name = "templateid") 
	private String templateid;
	
	@Column(name = "createdate") 
	private Date createdate;
	
	@Column(name = "deletedate") 
	private String deletedate;
	
	@Column(name = "homestore") 
	private String homestore;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getDeletedate() {
		return deletedate;
	}

	public void setDeletedate(String deletedate) {
		this.deletedate = deletedate;
	}

	public String getHomestore() {
		return homestore;
	}

	public void setHomestore(String homestore) {
		this.homestore = homestore;
	}

}
