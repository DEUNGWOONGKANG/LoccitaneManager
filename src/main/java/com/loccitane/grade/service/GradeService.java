package com.loccitane.grade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.repository.GradeRepository;

@Service // 서비스 클래스임을 나타냄
public class GradeService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	GradeRepository gradeRepo;
	
	// 모든 등급 조회
	public List<Grade> findAll(){
		List<Grade> list = gradeRepo.findAllByUseyn("Y");
		return list;
	}
}
