package com.loccitane.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "userdata") //대응하는 테이블 설정
public class User {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userseq") 
	private int seq;
	
	@Column(name = "usercode") 
	private String usercode;

	@Column(name = "grade")
	private String grade;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "birthday")
	private String birthday;

	@Column(name = "status")
	private String status;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "lastpurchase")
	private Date lastpurchase;
	
	@Column(name = "totalbuy")
	private int totalbuy;
	
	@Column(name = "lastupdate")
	private Date lastupdate;
	
	@Column(name = "alarmyn")
	private String alarmyn;
	
	@Column(name = "secondvisit_1")
	private Date second1date;
	
	@Column(name = "secondvisit_2")
	private Date second2date;
	
	@Column(name = "secondvisit_3")
	private Date second3date;
	
	@Column(name = "secondvisit_4")
	private Date second4date;
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAlarmyn() {
		return alarmyn;
	}

	public void setAlarmyn(String alarmyn) {
		this.alarmyn = alarmyn;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastpurchase() {
		return lastpurchase;
	}

	public void setLastpurchase(Date lastpurchase) {
		this.lastpurchase = lastpurchase;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public int getTotalbuy() {
		return totalbuy;
	}

	public void setTotalbuy(int totalbuy) {
		this.totalbuy = totalbuy;
	}

	public Date getSecond1date() {
		return second1date;
	}

	public void setSecond1date(Date second1date) {
		this.second1date = second1date;
	}

	public Date getSecond2date() {
		return second2date;
	}

	public void setSecond2date(Date second2date) {
		this.second2date = second2date;
	}

	public Date getSecond3date() {
		return second3date;
	}

	public void setSecond3date(Date second3date) {
		this.second3date = second3date;
	}

	public Date getSecond4date() {
		return second4date;
	}

	public void setSecond4date(Date second4date) {
		this.second4date = second4date;
	}
	
}
