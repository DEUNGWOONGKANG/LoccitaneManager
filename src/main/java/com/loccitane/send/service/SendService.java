package com.loccitane.send.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.send.domain.Send;
import com.loccitane.send.repository.SendRepository;

@Service // 서비스 클래스임을 나타냄
public class SendService {
	@Autowired // 스프링부트가 자동으로 객체를 주입해준다.
	SendRepository repo;
	
	public void sendSave(Send send) {
		repo.save(send);
	}

	public Page<Send> findAll(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		return repo.findAll(pageable);
	}

	public void sendSaveAll(List<Send> sendList) {
		repo.saveAll(sendList);
	}
}
