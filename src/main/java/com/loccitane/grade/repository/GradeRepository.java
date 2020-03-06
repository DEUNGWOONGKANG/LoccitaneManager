package com.loccitane.grade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.grade.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>{
	List<Grade> findAllByUseyn(String useyn);

	List<Grade> findAllByOrderByMinimumDesc();

	Grade findBySeq(int seq);

	List<Grade> findAllByOrderByMinimumAsc();
}
