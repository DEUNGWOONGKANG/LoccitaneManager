package com.loccitane.log.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loccitane.log.domain.RefundLog;
import com.loccitane.log.repository.RefundLogRepository;

@Service // 서비스 클래스임을 나타냄
public class RefundLogService {
	@Autowired
	RefundLogRepository repo;
	
	public List<RefundLog> getLog(){
		return repo.findAll();
	}
	
	public void saveLog(RefundLog log) {
		repo.save(log);
	}
	
}
