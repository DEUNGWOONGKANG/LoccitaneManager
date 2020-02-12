package com.loccitane.user.domain;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loccitane.coupon.domain.Coupon;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "lc_member_core") //대응하는 테이블 설정
public class User {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@Column(name = "lc_user_id") //대응하는 컬럼명 지정
	private String userid;

	@Column(name = "lc_user_pw")
	private String userpw;

	@Column(name = "lc_user_grade")
	private String grade;
	
	@Column(name = "lc_user_name")
	private String username;
	
	@Column(name = "lc_user_store")
	private String store;
	
	@Column(name = "lc_user_position")
	private String position;
	
	@Column(name = "lc_user_status")
	private String status;
	
	@Column(name = "lc_user_phone")
	private String phone;
	
	@Column(name = "lc_user_upd")
	private Date upate;

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

	public Date getUpate() {
		return upate;
	}

	public void setUpate(Date upate) {
		this.upate = upate;
	}
}
