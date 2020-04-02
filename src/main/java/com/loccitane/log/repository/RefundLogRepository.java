package com.loccitane.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.log.domain.RefundLog;

@Repository
public interface RefundLogRepository extends JpaRepository<RefundLog, Long> {
}
