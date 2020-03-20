package com.loccitane.log.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.log.domain.Log;
import com.loccitane.log.repository.LogRepository;

@Service // 서비스 클래스임을 나타냄
public class LogService {
	@Autowired
	LogRepository logRepo;
	
	public List<Log> getLog(String kind){
		List<Log> logData = logRepo.findAllByLogkindOrderBySeqDesc(kind);
		return logData;
	}
	
	public void saveLog(Log log) {
		logRepo.save(log);
	}
	
	public Page<Log> getLogs(Pageable pageable, String kind){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		return logRepo.findAllByLogkindOrderByLogdateDesc(kind, pageable);
	}

}
