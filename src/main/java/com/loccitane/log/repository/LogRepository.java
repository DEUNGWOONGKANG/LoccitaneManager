package com.loccitane.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.log.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	List<Log> findAllByLogkindOrderBySeqDesc(String logKind);
}
