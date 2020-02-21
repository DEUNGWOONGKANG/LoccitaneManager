package com.loccitane.grade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.grade.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String>{
	List<Grade> findAllByUseyn(String useyn);
}
