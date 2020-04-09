package com.loccitane.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

	Store findByIdAndPwAndCode(String loginId, String loginPw, String type);
	Store findByIdAndPwAndCodeNot(String loginId, String loginPw, String type);
	Page<Store> findAllByCodeNot(String code, Pageable pageable);
	Page<Store> findAllByNameContainingAndCodeNot(String searchKeyword, String code, Pageable pageable);
	Page<Store> findAllByTelContainingAndCodeNot(String searchKeyword, String code, Pageable pageable);
	Store findBySeq(int seq);
	Store findByCode(String homestore);
	Store findByCodeAndPw(String code, String oldPw);
}
