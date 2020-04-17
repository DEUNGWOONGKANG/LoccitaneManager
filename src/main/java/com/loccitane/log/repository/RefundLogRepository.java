package com.loccitane.log.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.log.domain.RefundLog;

@Repository
public interface RefundLogRepository extends JpaRepository<RefundLog, Long> {
	Page<RefundLog> findAllByUsernameContaining(String searchKeyword, Pageable pageable);
	Page<RefundLog> findAllByUsercodeContaining(String searchKeyword, Pageable pageable);
	Page<RefundLog> findAllByOrderBySeqDesc(Pageable pageable);
}
