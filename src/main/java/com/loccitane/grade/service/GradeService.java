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
	
	//사용가능한 모든 등급 조회
	public List<Grade> getGradeUse(){
		return gradeRepo.findAllByUseyn("Y");
	}

	//전체 등급 조회 DESC
	public List<Grade> findAll() {
		return gradeRepo.findAllByOrderByMinimumDesc();
	}
	
	//전체 등급 조회 ASC
	public List<Grade> findAllAsc() {
		return gradeRepo.findAllByUseynOrderByMinimumAsc("Y");
	}
	
	//등급 수정 하기위해 데이터 조회
	public Grade find(int seq) {
		return gradeRepo.findBySeq(seq);
	}
	
	//등급 저장
	public void gradeSave(Grade oldGrade) {
		gradeRepo.save(oldGrade);
	}
}
