package com.loccitane.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loccitane.store.domain.Store;
import com.loccitane.store.repository.StoreRepository;

@Service // 서비스 클래스임을 나타냄
public class StoreService {
	
	@Autowired
	StoreRepository storeRepo;
	
	public Page<Store> getStoreList(Pageable pageable, String searchKey, String searchKeyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        
		Page<Store> storeData = null;
		if(searchKey == null) {
			storeData = storeRepo.findAllByCodeNot("super", pageable);
		}else if(searchKey.equals("name")) {
			storeData = storeRepo.findAllByNameAndCodeNot(searchKeyword, "super", pageable);
		}else if(searchKey.equals("tel")) {
			storeData = storeRepo.findAllByTelAndCodeNot(searchKeyword, "super", pageable);
		}else{
			storeData = storeRepo.findAllByCodeNot("super", pageable);
		}
		
		return storeData;
	}
	
	// 관리자 로그인 
	public Store managerLogin(String loginId, String loginPw, String type) {
		Store storeData = null;
		
		if(type.equals("super")) { 
			//슈퍼관리자용 로그인
			storeData = storeRepo.findByIdAndPwAndCode(loginId, loginPw, type); 
		}else{
			//매장관리자용 로그인
			storeData = storeRepo.findByIdAndPw(loginId, loginPw); 
		}
		
		return storeData;
	}
	
	// 매장추가
	public void storeSave(Store store) {
		storeRepo.save(store);
	}
	
	public Store getStoreInfo(int seq) {
		Store storeData = storeRepo.findBySeq(seq);
	 
		return storeData;
	}

}
