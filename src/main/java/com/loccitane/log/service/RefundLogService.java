package com.loccitane.log.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.log.domain.RefundLog;
import com.loccitane.log.repository.RefundLogRepository;

@Service // 서비스 클래스임을 나타냄
public class RefundLogService {
	@Autowired
	RefundLogRepository repo;
	
	public Page<RefundLog> getLog(Pageable pageable, String searchKey, String searchKeyword){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<RefundLog> list = null;
		if(searchKey == null) {
			list = repo.findAllByOrderBySeqDesc(pageable);
		}else if(searchKey.equals("username")) {
			list = repo.findAllByUsernameContaining(searchKeyword, pageable);
		}else if(searchKey.equals("usercode")) {
			list = repo.findAllByUsercodeContaining(searchKeyword,  pageable);
		}else{
			list = repo.findAllByOrderBySeqDesc(pageable);
		}
		
		return list;
	}
	
	public void saveLog(RefundLog log) {
		repo.save(log);
	}
	
}
