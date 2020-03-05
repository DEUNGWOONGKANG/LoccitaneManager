package com.loccitane.grade.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loccitane.coupon.domain.Coupon;
import com.loccitane.grade.domain.Grade;
import com.loccitane.grade.service.GradeService;
import com.loccitane.user.domain.User;
import com.loccitane.utils.Paging;

@Controller
public class GradeController {
	@Autowired
	GradeService service;
	
	//등급 등록페이지 이동
	@GetMapping("/super/gradeadd")
	public ModelAndView gradeAdd() {
		ModelAndView nextView = new ModelAndView("super/superManagerGradeAdd");
		return nextView;
	}
	
	//등급 저장
	@RequestMapping("/super/gradesave") 
	public ModelAndView gradeSave(Grade grade, HttpServletRequest request){ 
		ModelAndView nextView = new ModelAndView("super/superManagerGradeList");

		String oldSeq = request.getParameter("oldseq");
		Grade oldGrade = null;
		if(oldSeq != null) {
			oldGrade = service.find(Integer.parseInt(oldSeq));
			oldGrade.setCode(grade.getCode());
			oldGrade.setKname(grade.getKname());
			oldGrade.setMemo(grade.getMemo());
			oldGrade.setMinimum(grade.getMinimum());
			oldGrade.setName(grade.getName());
			oldGrade.setUseyn(grade.getUseyn());
			service.gradeSave(oldGrade);
		}else{
			service.gradeSave(grade);
		}
		List<Grade> gradeList = service.findAll();
		
		nextView.addObject("saveyn", "Y");
		nextView.addObject("gradeList", gradeList);
  		
		return nextView;
	}
	
	//슈퍼관리자 사용자 정보보기
	@GetMapping("/super/gradeinfo/{seq}") 
	public ModelAndView gradeModify(@PathVariable("seq") int seq){ 
		ModelAndView nextView = new ModelAndView("super/superManagerGradeAdd");
		
		Grade oldGrade = service.find(seq);
		
		nextView.addObject("grade", oldGrade);
	
		return nextView;
	}
}
