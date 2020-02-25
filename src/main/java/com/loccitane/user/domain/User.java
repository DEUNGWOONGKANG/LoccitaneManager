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
	
	@Column(name = "vipcode") 
	private String vipcode;
	
	@Column(name = "userid") 
	private String userid;

	@Column(name = "userpw")
	private String userpw;

	@Column(name = "grade")
	private String grade;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "store")
	private String store;
	
	@Column(name = "position")
	private String position;
	
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

	public String getVipcode() {
		return vipcode;
	}

	public void setVipcode(String vipcode) {
		this.vipcode = vipcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
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

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	
	
	
}
