package com.loccitane.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "store") //대응하는 테이블 설정
public class Store {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "storeseq") //대응하는 컬럼명 지정
	private int seq;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "pw")
	private String pw;
	
	@Column(name = "managername")
	private String managername;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "postcode")
	private String postcode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longtitude")
	private String longtitude;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "storememo")
	private String memo;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}
	
}
