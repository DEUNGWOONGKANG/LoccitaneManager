package com.loccitane.send.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loccitane.send.domain.Send;

@Repository
public interface SendRepository extends JpaRepository<Send, Integer>{
}
