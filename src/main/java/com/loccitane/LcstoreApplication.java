package com.loccitane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.loccitane.utils.KakaoService;

@SpringBootApplication
@EnableScheduling
public class LcstoreApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LcstoreApplication.class, args);
	}

}
