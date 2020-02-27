package com.loccitane.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // domain클래스인것을 나타내는 어노테이션
@Table(name = "v_storetomember") //대응하는 테이블 설정
public class StoreView {
	@Id //Primary key의 컬럼인 것을 나타나주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "userseq") //대응하는 컬럼명 지정
	private int seq;
	
	@Column(name = "userid") //대응하는 컬럼명 지정
	private String userid;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "name")
	private String storename;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "postcode")
	private String postcode;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "storememo")
	private String storememo;
}
